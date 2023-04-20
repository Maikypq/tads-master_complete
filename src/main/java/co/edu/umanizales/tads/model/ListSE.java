package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Node;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
 @Getter
 public class ListSE {
     private Node head;

     private int size;
     private List<Kid> kids;

     /*
      Algoritmo de adicionar al final

      como Entreada recibimos a
         un niño
      si hay datos // pueden haber dos opciones por las que puedo tomar

      si
             llamar a un ayudante que se posicione en la cabeza y  compruebe si en el brazo
             hay algo, si existe ese algo, paso al siguiente
             hasta llegar al ultimo

            al llegar al ultimo meter al niño en un costal (nuevo costal)
             y le digo al ultimo que tome el nuevo costal y lo ubique en el ultimo lugar

       no

       metemos al niño en ese costal y ese costal es la cabeza

      */
     public void add(Kid kid){
         if (head != null){

             Node temp=head;
             while(temp.getNext() != null)
             {
                 temp=temp.getNext();
             }
             //parado en el ultimo
             Node newNode = new Node(kid);
             temp.setNext(newNode);
         }
         else{
             head= new Node(kid);
         }
         size ++;
     }
    /*
    Adicionar al inicio
    si hay datos
    puedo tener dos opciones
    si
    meto al niño en un costal (nuevo costal)
    le digo a nuevo costal que tome con su brazo a la cabeza
    cabea es igual a nuevo costal
    no
    meto al niño en un cosata y lo asigno a la cabezza
    */

     public void addToStart(Kid kid){
         if(head != null){
             Node newNode=new Node(kid);
             newNode.setNext(head);
             head = newNode;
         }
         else{
             head= new Node(kid);
         }
     }
    /*
 metodo de elinar elementos

 buscar el elemento que deseamos eliminar
 mirar en que lugar esta posicionado el elemento
 dependiento del lugar en que se encuentre el elemento cambiar al anterior o al siguiente
 borrar el elemento
    */

     //metodo para elimminar por identificacion
     public void deleteByIdentification(String identification) {
         Kid kidToRemove = null;
         for (Kid kid : kids) {
             if (kid.getIdentification().equals(identification)) {
                 kidToRemove = kid;
                 break;
             }
             if (kidToRemove != null) {
                 kids.remove(kidToRemove);
             }
         }
     }
     public Kid getKidByIdentification(String identification) {
         for (Kid kid : kids) {
             if (kid.getIdentification().equals(identification)) {
                 return kid;
             }
         }
         return null;
     }


     //metodo para añadir por posicion
     public void addByPosition(Kid kid , int position){
         Node newNode= new Node(kid);
         if(position==0){
             newNode.setNext(head);
             head = newNode;
         }else{
             Node current= head;
             for(int i=0 ; i<position-1;i++){
                 current=current.getNext();
             }
             newNode.setNext(current.getNext());
             current.setNext(newNode);
         }
     }
     //metodo para calcular el total de las edades
     public double getAverageAge(){
         int totalAge=0;
         int numKids = kids.size();
         for(Kid kid : kids){
             totalAge += kid.getAge();
         }
         return (double)totalAge/numKids;
     }
     //metodo para calcular el total de niños

     public int getTotalKids(){
         int sum=0;
         for(Kid kid:kids){
             sum = sum + getTotalKids();
         }
         return sum;
     }
     //metodo para intercambiar extremos
     public void changeExtremes(){
         if(this.head !=null && this.head.getNext() != null){
             Node temp = this.head;
             while(temp.getNext()!= null){
                 temp = temp.getNext();
             }
             //temp esta en el ultimo
             Kid copy = this.head.getData();
             this.head.setData(temp.getData());
             temp.setData(copy);
         }
     }
     public void invert(){
         if(this.head != null){
             ListSE listCp= new ListSE();
             Node temp = this.head;
             while(temp!= null){
                 listCp.addToStart(temp.getData());
                 temp = temp.getNext();
             }
         }
     }
     public void orderBoyToStart(){
         if(this.head != null){
             ListSE listCp = new ListSE();
             Node temp = this.head;
             while(temp != null){
                 if(temp.getData().getGender()=='M'){
                     listCp.addToStart(temp.getData());
                 }
                 else {
                     listCp.add(temp.getData());
                 }
                 temp= temp.getNext();
             }
             this.head = listCp.getHead();
         }
     }
     public int getCountKidsByLocation(String code){
         int count =0;
         if(this.head !=null){
             Node temp= this.head;
             while (temp != null){
                 if(temp.getData().getLocation().getCode().equals(code)){
                     count++;
                 }
                 temp = temp.getNext();
             }
         }
         return count;
     }
     public int getCountKidsByDeptoCode(String code){
         int count =0;
         if( this.head!=null){
             Node temp = this.head;
             while(temp != null){
                 if(temp.getData().getLocation().getCode().substring(0,5).equals(code)){
                     count++;
                 }
                 temp = temp.getNext();
             }
         }
         return count;
     }
    // Codigo parcial 1 (ELIMINAR A UN NIÑO POR UNA EDAD DADA)
    public void deleteByAge(int age) {
        Node current = head;
        Node prev = null;

        while (current != null) {
            if (current.getData().getAge() == age) {
                if (prev == null) {
                    // Si el niño a eliminar está al inicio de la lista
                    head = current.getNext();
                } else {
                    // Si el niño a eliminar está en otro lugar de la lista
                    prev.setNext(current.getNext());
                }
            } else {
                prev = current;
            }
            current = current.getNext();
        }
    }

    // Codigo parcial 1 (DEFINIRLE A UN NIÑO QUE ADELANTE UN NUMERO DE POSICIONES DADAS)

    public void moveKidToPosition(Kid kid, int newPosition) {
        if (head == null) {
            // la lista está vacía, no se puede mover ningún niño
            return;
        }

        Node currentNode = head;
        Node prevNode = null;
        int currentPosition = 0;

        // Busca el nodo que contiene al niño que se desea mover
        while (currentNode != null && !currentNode.getData().equals(kid)) {
            prevNode = currentNode;
            currentNode = currentNode.getNext();
            currentPosition++;
        }

        if (currentNode == null) {
            // el niño no se encuentra en la lista, no se puede mover
            return;
        }

        if (newPosition < 0 || newPosition >= currentPosition) {
            // la posición a la que se desea mover el niño no es válida
            return;
        }

        // elimina el nodo que contiene al niño de la lista
        if (prevNode == null) {
            // el nodo a eliminar es el primero de la lista
            head = currentNode.getNext();
        } else {
            prevNode.setNext(currentNode.getNext());
        }

        // inserta el nodo en la nueva posición
        Node newNode = new Node(kid);
        if (newPosition == 0) {
            // el nodo se inserta al principio de la lista
            newNode.setNext(head);
            head = newNode;
        } else {
            // busca el nodo que va justo antes de la nueva posición
            Node actual = head;
            for (int i = 0; i < newPosition - 1; i++) {
                actual = actual.getNext();
            }
            // inserta el nodo después de ese nodo
            newNode.setNext(actual.getNext());
            actual.setNext(newNode);
        }

    }
}
