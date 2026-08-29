// Classe di STUDIO sui Generics (tipi generici) in Java
// I Generics permettono di creare classi che funzionano con qualsiasi tipo di oggetto
// senza doverle riscrivere ogni volta
//
// Esempio d'uso:
//   ArrayList2<String, Integer> lista = new ArrayList2<>();
//   → E diventa String, T diventa Integer per tutta questa istanza
//
// Le lettere tra <> sono solo nomi simbolici — per convenzione si usa:
//   E = Element (tipo degli elementi)
//   T = Type (tipo generico)
//   K = Key, V = Value (usati nelle mappe)
public class ArrayList2<E, T> {

    // ritorna un elemento di tipo E (il primo tipo generico scelto alla creazione)
    // ora ritorna null perché è solo uno scheletro dimostrativo
    public E getFirst() {
        return null;
    }

    // ritorna un elemento di tipo T (il secondo tipo generico scelto alla creazione)
    public T getSecond() {
        return null;
    }

    // metodo che accetta ENTRAMBI i tipi generici come parametri
    // può ricevere sia un E che un T nello stesso metodo
    public void getThird(E e, T t) {
        // corpo vuoto: è solo un esempio di firma del metodo
    }

    /*
    // NOTA STUDIO - extends nei generici:
    // public void getThird(A a, B extends SomeClass b) { }
    // "extends" nel parametro generico significa: accetta solo classi che
    // estendono (o sono) SomeClass → utile per limitare i tipi accettati
    */
}
