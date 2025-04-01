package com.nhlstenden.route;

import com.google.common.reflect.TypeToken;
import com.nhlstenden.controllers.JsonUtils;
import com.nhlstenden.controllers.SharedData;
import com.nhlstenden.middelware.BloomFilterMiddelware;
import com.nhlstenden.middelware.MyArrayList;
import com.nhlstenden.services.JobApplicationService;
import io.javalin.Javalin;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.function.Function;

public class MainRoute<T> {
	private final JobApplicationService<T> jobApplicationService;
	private SharedData<T> data;
	private SearchRoutes<T> searchRoutes;
	private SortAlgo<T> sortAlgo;
	private BloomFilterMiddelware<T> bloomFilter;
	private boolean routesConfigured = false;
	private PriorityQueueRoute<T> priorityQueueRoute;
	private BayerMooreRoute<T> bayerMooreRoute;

	public MainRoute(JobApplicationService<T> jobApplicationService) {
		this.jobApplicationService = jobApplicationService;
	}

	public void configureRoutes(Javalin app) {
		app.post("/upload", ctx -> {
			ctx.uploadedFiles("files").forEach(uploadedFile -> {
				InputStreamReader reader = new InputStreamReader(uploadedFile.content());
				try {
					Type type = new TypeToken<MyArrayList<T>>() {}.getType();
					MyArrayList<T> resultArray = JsonUtils.jsonFileToMapGson(reader, type);

					if (this.data == null) {
						this.data = new SharedData<>(resultArray);
						this.bloomFilter = new BloomFilterMiddelware<>(resultArray.size(), new Function[]{Object::hashCode});
						for (T element : resultArray) {
							this.bloomFilter.add(element);
						}
					} else {
						for (T element : resultArray) {
							if (!this.bloomFilter.mightContain(element)) {
								this.data.getSharedArray().add(element);
								this.bloomFilter.add(element);
							} else {
								boolean isDuplicate = false;
								for (T dataElement : this.data.getSharedArray()) {
									if (dataElement.equals(element)) {
										isDuplicate = true;
										break;
									}
								}
								if (!isDuplicate) {
									this.data.getSharedArray().add(element);
									this.bloomFilter.add(element);
								}
							}
						}
					}

					jobApplicationService.addApplications(this.data.getSharedArray());

					ctx.status(200).result("Completed");

					if (!routesConfigured) {
						searchRoutes = new SearchRoutes<>(data);
						searchRoutes.configureRoutes(app);
						sortAlgo = new SortAlgo<>(data);
						sortAlgo.configureRoutes(app);
						priorityQueueRoute = new PriorityQueueRoute<>(data);
						priorityQueueRoute.configureRoutes(app);
						bayerMooreRoute = new BayerMooreRoute<>(data);
						bayerMooreRoute.configureRoutes(app);

						this.routesConfigured = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					ctx.status(500).result("Missing file");
				}
			});
		});
	}
}