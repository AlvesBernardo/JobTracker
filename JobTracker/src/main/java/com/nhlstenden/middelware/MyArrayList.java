package com.nhlstenden.middelware;

import java.util.*;

//import com.allendowney.thinkdast.Profiler.Timeable;
//import org.jfree.data.xy.XYSeries;


/**
 * @param <T>
 * @author downey
 */
public class MyArrayList<T> implements List<T>
{
    int size;                    // keeps track of the number of elements
    private T[] array;           // stores the elements

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public MyArrayList()
    {
        // You can't instantiate an array of T[], but you can instantiate an
        // array of Object and then typecast it.  Details at
        // http://www.ibm.com/developerworks/java/library/j-jtp01255/index.html
        array = (T[]) new Object[10];
        size = 0;
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // run a few simple tests
        MyArrayList<Integer> mal = new MyArrayList<Integer>();
        mal.add(1);
        mal.add(2);
        mal.add(3);
        System.out.println(Arrays.toString(mal.toArray()) + " size = " + mal.size);

        mal.remove(Integer.valueOf(2));
        System.out.println(Arrays.toString(mal.toArray()) + " size = " + mal.size);
    }

    @Override
    public boolean add(T element)
    {
        if (size >= array.length)
        {
            T[] bigger = (T[]) new Object[array.length * 2];
            System.arraycopy(array, 0, bigger, 0, array.length);
            array = bigger;
        }

        array[size] = element;
        size++;
        return true;
    }

    @Override
    public void add(int index, T element)
    {
        if (index < 0 || index > size)
        {
            throw new IndexOutOfBoundsException();
        }
        // add the element to get the resizing
        add(element);

        // shift the elements
        for (int i = size - 1; i > index; i--)
        {
            array[i] = array[i - 1];
        }
        // put the new one in the right place
        array[index] = element;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection)
    {
        boolean flag = true;
        for (T element : collection)
        {
            flag &= add(element);
        }
        return flag;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear()
    {
        // note: this version does not actually null out the references
        // in the array, so it might delay garbage collection.
        size = 0;
    }

    @Override
    public boolean contains(Object obj)
    {
        return indexOf(obj) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> collection)
    {
        for (Object element : collection)
        {
            if (!contains(element))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public T get(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }

        return array[index];

    }


    @Override
    public int indexOf(Object target)
    {
        for (int i = 0; i < this.size; i++)
        {
            if (equals(target, array[i]))
            {
                return i;
            }
        }

        return -1;
    }


    /** returns the index of the first occurence of the element in the list
     . it will return -1 if the list does not contain the element
     **/

    /**
     * Checks whether an element of the array is the target.
     * <p>
     * Handles the special case that the target is null.
     *
     * @param target
     * @param object
     */

    private boolean equals(Object target, Object element)
    {
        if (target == null)
        {
            return element == null;
        } else
        {
            return target.equals(element);
        }
    }


    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator()
    {
        // make a copy of the array
        T[] copy = Arrays.copyOf(array, size);
        // make a list and return an iterator
        return Arrays.asList(copy).iterator();
    }

    @Override
    public int lastIndexOf(Object target)
    {
        // see notes on indexOf
        for (int i = size - 1; i >= 0; i--)
        {
            if (equals(target, array[i]))
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator()
    {
        // make a copy of the array
        T[] copy = Arrays.copyOf(array, size);
        // make a list and return an iterator
        return Arrays.asList(copy).listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index)
    {
        // make a copy of the array
        T[] copy = Arrays.copyOf(array, size);
        // make a list and return an iterator
        return Arrays.asList(copy).listIterator(index);
    }

    @Override
    public boolean remove(Object obj)
    {
        int index = indexOf(obj);
        if (index == -1)
        {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public T remove(int index)
    {
        T element = get(index);
        if (index > this.array.length || index < 0)
        {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < this.array.length; i++)
        {
            array[i] = array[i + 1];
        }
        size--;
        return element;
    }

    /**
     * removes the elemet of a specifc position
     **/

    @Override
    public boolean removeAll(Collection<?> collection)
    {
        boolean flag = true;
        for (Object obj : collection)
        {
            flag &= remove(obj);
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element)
    {
        T old = get(index);
        this.array[index] = element;
        return old;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex)
    {
        if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex)
        {
            throw new IndexOutOfBoundsException();
        }
        T[] copy = Arrays.copyOfRange(array, fromIndex, toIndex);
        return Arrays.asList(copy);
    }

    @Override
    public Object[] toArray()
    {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <U> U[] toArray(U[] array)
    {
        throw new UnsupportedOperationException();
    }

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < this.size(); i++) {
			sb.append(this.get(i).toString());
			if (i < this.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

//	private static void runProfiler(String title, Timeable timeable, int startN, int endMillis){
//		Profiler profiler = new Profiler(title, timeable);
//		XYSeries series = profiler.timingLoop(startN, endMillis);
//		profiler.plotResults(series);
//	}
}
