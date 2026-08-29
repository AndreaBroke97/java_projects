public class Box<T> { //segna posto del tipo <T>
    private T content;

    public void set(T content) {
        this.content = content;
    }


    public T get() {
        return content;
    }
}
