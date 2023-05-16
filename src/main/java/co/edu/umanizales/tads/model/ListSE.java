package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

@Data
public class ListSE {
    private Node head;
    private int size;

    private Node tail;

    /*
    Algoritmo de adicionar al final
     */
    public void add(Kid kid) throws ListSEException {
        if(head != null){
            Node temp = head;
            while(temp.getNext() !=null)
            {
                if(temp.getData().getIdentification().equals(kid.getIdentification())){
                    throw new ListSEException("Ya existe un niño");
                }
                temp = temp.getNext();

            }
            if(temp.getData().getIdentification().equals(kid.getIdentification())){
                throw new ListSEException("Ya existe un niño");
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        }
        else {
            head = new Node(kid);
        }
        size ++;
    }

    /* Adicionar al inicio
     */
    public void addToStart(Kid kid){
        if(head !=null)
        {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        }
        else {
            head = new Node(kid);
        }
        size++;
    }
    public void deleteByidentification (String identification){
        Node currentNode = head;
        Node prevNode = null;

        while (currentNode != null && currentNode.getData().getIdentification() != identification) {
            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

        if(currentNode != null){
            if (prevNode == null){
                head = currentNode.getNext();
            }else {
                prevNode.setNext(currentNode.getNext());
            }
        }
    }
    public void addKidsByPosition(Kid kid, int pos){
        Node newNode = new Node(kid);
        if (pos == 0){
            newNode.setNext(head);
            head = newNode;
        } else {
            Node current = head;
            for (int i = 1; i < pos - 1; i++){
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            current.setNext(newNode);
        }
    }

//1.Invertir Lista

public void invert() throws ListSEException{
    if(this.head !=null){
        ListSE listCp = new ListSE();
        Node temp = this.head;
        while(temp != null){
            listCp.addToStart(temp.getData());
            temp = temp.getNext();
        }
        this.head = listCp.getHead();
    }
    else {
        throw new ListSEException("No hay niños para poder invertir la lista");
    }
}

    //2.Niños al inicio y niñas al final
    public void orderBoysToStart() throws ListSEException{
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getGender()=='M')
                {
                    listCp.addToStart(temp.getData());
                }
                else{
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    //3.intercalar niño,niña,niño,niña

    public void interleaveBoysAndGirls() throws ListSEException {
        ListSE listCp = new ListSE();
        ListSE boysList = new ListSE();
        ListSE girlsList = new ListSE();

        Node temp = head;

        if (this.head == null && this.head.getNext() == null) {
            throw new ListSEException("No existen niños o no hay suficientes para alternar");
        } else {
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    boysList.add(temp.getData());
                } else {
                    if (temp.getData().getGender() == 'F') {
                        girlsList.add(temp.getData());
                    }
                }
                temp = temp.getNext();
            }

            Node nodeBoys = boysList.getHead();
            Node nodeGirls = girlsList.getHead();

            while (nodeBoys != null) {
                if (nodeBoys != null) {
                    listCp.add(nodeBoys.getData());
                    nodeBoys = nodeBoys.getNext();
                }
                if (nodeGirls != null) {
                    listCp.add(nodeGirls.getData());
                    nodeGirls = nodeGirls.getNext();
                }
            }
            this.head = listCp.getHead();
        }
    }


    //4. Dada una edad eliminar a los niños de la edad dada
    public void removeBoysByAge(int age) {
        if (this.head == null) {
            // La lista está vacía, no hay nada que eliminar
            return;
        }

        Node current = this.head;
        Node previous = null;

        while (current != null) {
            if (current.getData().getGender() == 'M' && current.getData().getAge() == age) {
                if (previous == null) {
                    // Si el primer elemento cumple las condiciones, se actualiza la cabeza de la lista
                    this.head = current.getNext();
                } else {
                    // De lo contrario, se actualiza el puntero del nodo anterior
                    previous.setNext(current.getNext());
                }
            } else {
                // El nodo actual no cumple las condiciones, se actualizan los punteros
                previous = current;
            }
            // Se avanza al siguiente nodo
            current = current.getNext();
        }
    }

    //5. Obtener el promedio de edad de los niños de la lista
    public double getAverageAgeBoys() {
        if (this.head == null) {
            // La lista está vacía, no hay niños que promediar
            return 0;
        }

        int totalAge = 0;
        int countBoys = 0;

        Node current = this.head;

        while (current != null) {
            if (current.getData().getGender() == 'M') {
                totalAge += current.getData().getAge();
                countBoys++;
            }
            current = current.getNext();
        }

        if (countBoys == 0) {
            // No hay niños en la lista, no se puede calcular el promedio
            return 0;
        }

        double averageAge = (double) totalAge / countBoys;
        return averageAge;
    }

    //6. Generar un reporte que me diga cuantos niños hay de cada ciudad.
    public int getCountKidsByLocationCode(String code){
        int count =0;
        if( this.head!=null){
            Node temp = this.head;
            while(temp != null){
                if(temp.getData().getLocation().getCode().equals(code)){
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    //7.Método que me permita decirle a un niño determinado que adelante un numero de posiciones dadas

    public void movekid(String id, int position, ListSE listSE) throws ListSEException{
        if (head != null){
            Node temp = this.head;
            int counter = 0;

            while (temp != null && ! temp.getData().getIdentification().equals(id)){
                temp = temp.getNext();
                counter ++;
            }
            int newPosition = counter - position;

            Kid listCopy = temp.getData();

            listSE.deleteByidentification(temp.getData().getIdentification());

            listSE.addKidsByPosition(listCopy , newPosition);
        }
        else {
            throw new ListSEException("La lista esta vacia por lo tanto no se puede completar la accion");
        }
    }

    //8.Método que me permita decirle a un niño determinado que pierda un numero de posiciones dadas
    public void losePosition(String id, int position, ListSE listSE) throws ListSEException{
        if (head != null){
            Node temp = this.head;
            int counter = 1;

            while (temp != null && ! temp.getData().getIdentification().equals(id)){
                temp = temp.getNext();
                counter ++;
            }
            int newPosition = position+counter;
            Kid listCopy = temp.getData();
            listSE.deleteByidentification(temp.getData().getIdentification());
            listSE.addKidsByPosition(listCopy , newPosition);
        }
        else {
            throw new ListSEException("La lista esta vacia por lo tanto no se puede completar la accion");
        }
    }

    //9.Obtener un informe de niños por rango de edades
    public int getRangeByAge(int first, int last) throws ListSEException{
        Node temp = head;
        int count = 0;
        while (temp != null){
            if (temp.getData().getAge() >= first && temp.getData().getAge() <= last){
                count ++;
            }
            temp = temp.getNext();
        }
        return count;
    }

    //10. Implementar un método que me permita enviar al final de la lista a los niños que su nombre inicie con una letra dada
    public void moveToEndByInitial(char letter) throws ListSEException{
        ListSE listCopy = new ListSE();
        Node temp = this.head;

        while (temp != null) {
            if (temp.getData().getName().charAt(0) != Character.toUpperCase(letter)) {
                listCopy.add(temp.getData());
            }
            temp = temp.getNext();
        }

        temp = this.head;

        while (temp != null) {
            if (temp.getData().getName().charAt(0) == Character.toUpperCase(letter)) {
                listCopy.add(temp.getData());
            }
            temp = temp.getNext();
        }
        this.head = listCopy.getHead();
    }





    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsLocationGenderDTO report){
        if(head !=null){
            Node temp = this.head;
            while(temp!=null){
                if(temp.getData().getAge()>age){
                    report.updateQuantity(
                            temp.getData().getLocation().getName(),
                            temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        }
    }

    public void changeExtremes(){
        if(this.head !=null && this.head.getNext() !=null)
        {
            Node temp = this.head;
            while(temp.getNext()!=null)
            {
                temp = temp.getNext();
            }
            //temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }

    }
}
