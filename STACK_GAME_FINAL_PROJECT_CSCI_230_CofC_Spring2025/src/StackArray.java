// Written by Julian Maiorino, Michelle Clark
// CSCI 230 Spring 2025
// College of Charleston 

// holds tetris blocks that user will be given next in GameBoard class
// implemented as int array in the class for now, uses integer to determine shape 
// from BlockTypes and to then determine coordinates to fill for shape type
public class StackArray <T>
{
    private Object[] stackArr;
    private int maxSize;
    private int head;

    public StackArray(int size)
    {
        maxSize = size;
        stackArr = new Object[maxSize];
        head = -1;
    }

    public boolean isFull()
    {
        return head == maxSize - 1;
    }

    public boolean isEmpty()
    {
        return head == -1;
    }

    public void push(T item)
    {
        if(isFull())
            return;
        stackArr[++head] = item;
    }

    public T pop()
    {
        if(isEmpty())
        {
            System.out.println("Stack is empty!"); // no exception throw as of now to avoid crashing program, 
                                                     //but could add one
            return null;
        }
        return (T) stackArr[head--];
        
    }

    public T peek()
    {
        if(isEmpty())
        {
            System.out.println("Stack is empty!");
            return null;
        }
        return (T) stackArr[head];
    }
}
