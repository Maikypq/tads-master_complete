package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.KidDTO;
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

//1.Invertir Lista
    public void invert(){
        if(this.head !=null){
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while(temp != null){
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
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

         //Adicionar al final
    public void addToEnd(Kid data) {
        Node newNode = new Node(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }

        size++;
    }
    public void interleaveBoysAndGirls() throws ListSEException {
        if (head == null) {
            return;
        }

        ListSE boysList = new ListSE();
        ListSE girlsList = new ListSE();

        // Separa los niños y las niñas en listas diferentes
        Node current = head;
        while (current != null) {
            if ("masculino".equals(current.getData().getGender())) {
                boysList.addToEnd(current.getData());
            } else {
                girlsList.addToEnd(current.getData());
            }
            current = current.getNext();
        }

        // Intercale los niños y las niñas alternativamente

        ListSE interleavedList = new ListSE();
        Node boyNode = boysList.getHead();
        Node girlNode = girlsList.getHead();
        while (boyNode != null && girlNode != null) {
            interleavedList.addToEnd(boyNode.getData());
            interleavedList.addToEnd(girlNode.getData());
            boyNode = boyNode.getNext();
            girlNode = girlNode.getNext();
        }

        // Agrega los niños restantes si hay más niños que niñas

        while (boyNode != null) {
            interleavedList.addToEnd(boyNode.getData());
            boyNode = boyNode.getNext();
        }

        // Agrega las niñas restantes si hay más niñas que niños

        while (girlNode != null) {
            interleavedList.addToEnd(girlNode.getData());
            girlNode = girlNode.getNext();
        }

        head = interleavedList.getHead();
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

    //agregar al principio de la lista
    public void addToHead(Kid data) {
        Node newNode = new Node(data);
        newNode.setNext(head);
        head = newNode;
    }
    public void moveKid(Kid kid, int positionsToMove) throws ListSEException {
        Node prevNode = null;
        Node currentNode = head;

        // Recorrer la lista hasta encontrar el nodo que contiene al niño buscado
        while (currentNode != null) {
            if (currentNode.getData().equals(kid)) {
                break;
            }
            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

        // Si no se encontró el nodo del niño, lanzar una excepción
        if (currentNode == null) {
            throw new ListSEException("El niño no está en la lista");
        }

        // Si el nodo es el primer elemento de la lista, simplemente actualizar la cabeza de la lista
        if (prevNode == null) {
            head = currentNode.getNext();
            currentNode.setNext(null);
            addToHead(currentNode.getData());
        } else {
            // Si no es el primer elemento, mover el nodo a la posición deseada
            int count = 0;
            Node targetNode = prevNode;
            while (count < positionsToMove && targetNode.getNext() != null) {
                targetNode = targetNode.getNext();
                count++;
            }
            prevNode.setNext(currentNode.getNext());
            currentNode.setNext(targetNode.getNext());
            targetNode.setNext(currentNode);
        }
    }

    //8.Método que me permita decirle a un niño determinado que pierda un numero de posiciones dadas
    public void losePosition(Kid kid, int positions) throws ListSEException {
        Node prevNode = null;
        Node currentNode = head;
        Node nextNode = null;

        // Buscar el nodo que contiene al niño que se quiere mover
        while (currentNode != null && !currentNode.getData().equals(kid)) {
            prevNode = currentNode;
            currentNode = currentNode.getNext();
        }

        // Si no se encuentra al niño, lanzar una excepción
        if (currentNode == null) {
            throw new ListSEException("El niño no se encuentra en la lista");
        }

        // Iterar sobre la lista para mover al niño a su nueva posición
        for (int i = 0; i < positions; i++) {
            // Si el niño está en la cabeza de la lista, no se puede mover hacia atrás
            if (currentNode == head) {
                break;
            }
            nextNode = currentNode.getNext();
            currentNode.setNext(prevNode);
            prevNode = currentNode;
            currentNode = nextNode;
        }

        // Si el niño no pudo moverse las posiciones deseadas, no se hace ningún cambio en la lista
        if (currentNode == head) {
            return;
        }

        // Ajustar los punteros para que el niño se mueva a su nueva posición
        Node newNextNode = prevNode;
        Node newCurrentNode = currentNode;
        Node newPrevNode = null;
        while (newCurrentNode.getNext() != currentNode) {
            newPrevNode = newCurrentNode;
            newCurrentNode = newCurrentNode.getNext();
        }
        newCurrentNode.setNext(newNextNode);
        if (newPrevNode == null) {
            head = newCurrentNode;
        } else {
            newPrevNode.setNext(newCurrentNode);
        }
    }

    //9.Obtener un informe de niños por rango de edades
    public KidDTO[] getKidsByAgeRange(int minAge, int maxAge) {
        // Contar la cantidad de niños dentro del rango especificado
        int count = 0;
        Node current = head;
        while (current != null) {
            Kid kid = current.getData();
            int age = kid.getAge();
            if (age >= minAge && age <= maxAge) {
                count++;
            }
            current = current.getNext();
        }

        // Inicializar el arreglo con el tamaño determinado
        KidDTO[] kidsInRange = new KidDTO[count];

        // Agregar los niños dentro del rango en el arreglo
        int i = 0;
        current = head;
        while (current != null) {
            Kid kid = current.getData();
            int age = kid.getAge();
            if (age >= minAge && age <= maxAge) {
                KidDTO kidDTO = new KidDTO();
                kidsInRange[i++] = kidDTO;
            }
            current = current.getNext();
        }

        return kidsInRange;
    }
    //10. Implementar un método que me permita enviar al final de la lista a los niños que su nombre inicie con una letra dada
    public void moveToEndByInitial(String initial) {
        if (head == null) {
            return;
        }
        Node current = head;
        Node previous = null;
        while (current != null) {
            Kid kid = current.getData();
            if (kid.getName().startsWith(initial)) {
                if (previous == null) {
                    head = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }
                if (current.getNext() == null) {
                    tail = previous;
                }
                tail.setNext(current);
                current.setNext(null);
                current = previous.getNext();
            } else {
                previous = current;
                current = current.getNext();
            }
        }
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
