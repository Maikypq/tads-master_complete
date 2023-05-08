package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;

@Data
public class ListDE {
    private Node head;
    private Node tail;
    private int size;

    // constructor
    public ListDE() {
        head = null;
        tail = null;
        size = 0;
    }

    public void addToStart(Kid kid) {
        Node newNode = new Node(kid);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
        size++;
    }

    public void add(Kid kid) throws ListDEException {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                    throw new ListDEException("Ya existe un niño");
                }
                temp = temp.getNext();
            }
            if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                throw new ListDEException("Ya existe un niño");
            }
            Node newNode = new Node(kid);
            temp.setNext(newNode);
            newNode.setPrev(temp); // nuevo nodo apunta al anterior
        } else {
            head = new Node(kid);
        }
        size++;
    }

    public Node searchByName(String name) {
        Node current = head;
        while (current != null) {
            if (current.getData().getName().equals(name)) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    //1. invertir lista

    public void invert() {
        if (head != null) {
            Node temp = head;
            while (temp != null) {
                Node prevNode = temp.getPrev();
                temp.setPrev(temp.getNext());
                temp.setNext(prevNode);
                temp = temp.getPrev();
            }
            Node oldHead = head;
            head = tail;
            tail = oldHead;
        }
    }

    //2. Niños al inicio y niñas al final
    public void orderBoysToStart() throws ListDEException {
        if (this.head != null) {
            ListDE listCp = new ListDE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
            this.tail = listCp.getTail();
        } else {
            throw new ListDEException("List is empty");
        }
    }

    //3. Intercalar niño, niña, niño, niña
    public void interleaveBoysAndGirls() throws ListDEException {
        if (head == null) {
            return;
        }

        ListDE boysList = new ListDE();
        ListDE girlsList = new ListDE();

        // Separa los niños y las niñas en listas diferentes
        Node current = head;
        while (current != null) {
            if ("masculino".equals(current.getData().getGender())) {
                boysList.add(current.getData());
            } else {
                girlsList.add(current.getData());
            }
            current = current.getNext();
        }

        // Intercale los niños y las niñas alternativamente
        head = null; // Reinicia la lista original
        Node boyNode = boysList.getHead();
        Node girlNode = girlsList.getHead();
        while (boyNode != null && girlNode != null) {
            Node boyNext = boyNode.getNext();
            Node girlNext = girlNode.getNext();

            boyNode.setNext(girlNode);
            girlNode.setPrev(boyNode);

            if (head == null) {
                head = boyNode;
            }

            boyNode = boyNext;
            girlNode = girlNext;
        }

        // Agrega los nodos restantes de la lista de niños o de niñas
        ListDE remainingList = (boyNode != null) ? boysList : girlsList;
        Node remainingNode = (boyNode != null) ? boyNode : girlNode;

        while (remainingNode != null) {
            Node remainingNext = remainingNode.getNext();

            remainingList.add(remainingNode.getData());

            remainingNode = remainingNext;
        }
    }

    //4. Dada una edad eliminar a los niños de la edad dada
    public void removeBoysByAge(int age) {
        if (this.head == null) {
            // La lista está vacía, no hay nada que eliminar
            return;
        }

        Node current = this.head;

        while (current != null) {
            if (current.getData().getGender() == 'M' && current.getData().getAge() == age) {
                if (current == this.head) {
                    // Si el primer elemento cumple las condiciones, se actualiza la cabeza de la lista
                    this.head = current.getNext();
                    if (this.head != null) {
                        this.head.setPrev(null);
                    }
                } else if (current.getNext() == null) {
                    // Si el último elemento cumple las condiciones, se actualiza el puntero del nodo anterior
                    current.getPrev().setNext(null);
                } else {
                    // De lo contrario, se actualizan los punteros
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
                }
            }
            // Se avanza al siguiente nodo
            current = current.getNext();
        }
    }

    //5. Obtener el promedio de edad de los niños de la lista

    public double getAverageAgeBoys() {
        if (head == null) {
            // La lista está vacía, no hay niños que promediar
            return 0;
        }

        int totalAge = 0;
        int countBoys = 0;

        Node current = head;

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

    //6. Generar un reporte que me diga cuantos niños hay de cada ciudad.    Chinchiná 3, Manizales 4, Pereira 5
    public int getCountKidsByLocationCode(String code) {
        int count = 0;
        Node current = head;
        while (current != null) {
            if (current.getData().getLocation().getCode().equals(code)) {
                count++;
            }
            current = current.getNext();
        }
        return count;
    }

    //7. Método que me permita decirle a un niño determinado que adelante un numero de posiciones dadas

    // Agregar al principio de la lista
    public void addToHead(Kid data) {
        Node newNode = new Node(data);
        if (head == null) {
            // Si la lista está vacía, el nuevo nodo es tanto la cabeza como la cola
            head = newNode;
            tail = newNode;
        } else {
            // Si la lista no está vacía, se inserta el nuevo nodo al principio
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }
    }

    public void moveKid(Kid kid, int positionsToMove) throws ListDEException {
        Node currentNode = head;

        // Recorrer la lista hasta encontrar el nodo que contiene al niño buscado
        while (currentNode != null) {
            if (currentNode.getData().equals(kid)) {
                break;
            }
            currentNode = currentNode.getNext();
        }

        // Si no se encontró el nodo del niño, lanzar una excepción
        if (currentNode == null) {
            throw new ListDEException("El niño no está en la lista");
        }

        // Si el nodo es el primer elemento de la lista, simplemente actualizar la cabeza de la lista
        if (currentNode == head) {
            head = currentNode.getNext();
            if (head != null) {
                head.setPrev(null);
            } else {
                // Si no hay más elementos en la lista, la cola también se actualiza
                tail = null;
            }
        } else {
            // Si el nodo es el último elemento de la lista, simplemente actualizar la cola de la lista
            if (currentNode == tail) {
                tail = currentNode.getPrev();
                tail.setNext(null);
            } else {
                // Si no es ni el primer ni el último elemento, mover el nodo a la posición deseada
                Node previousNode = currentNode.getPrev();
                Node targetNode = previousNode;
                for (int i = 0; i < positionsToMove && targetNode != null; i++) {
                    targetNode = targetNode.getNext();
                }
                if (targetNode == null) {
                    // Si se llega al final de la lista, se inserta el nodo al final
                    previousNode.setNext(currentNode.getNext());
                    if (currentNode.getNext() != null) {
                        currentNode.getNext().setPrev(previousNode);
                    }
                    currentNode.setPrev(tail);
                    currentNode.setNext(null);
                    tail.setNext(currentNode);
                    tail = currentNode;
                } else {
                    // Si no se llega al final de la lista, se inserta el nodo en la posición deseada
                    previousNode.setNext(currentNode.getNext());
                    if (currentNode.getNext() != null) {
                        currentNode.getNext().setPrev(previousNode);
                    }
                    currentNode.setPrev(targetNode.getPrev());
                    currentNode.setNext(targetNode);
                    targetNode.getPrev().setNext(currentNode);
                    targetNode.setPrev(currentNode);
                }
            }
        }
    }

    //8. Método que me permita decirle a un niño determinado que pierda un numero de posiciones dadas
    public void losePosition(Kid kid, int positions) throws ListDEException {
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
            throw new ListDEException("El niño no se encuentra en la lista");
        }

        // Iterar sobre la lista para mover al niño a su nueva posición
        for (int i = 0; i < positions; i++) {
            // Si el niño está en la cabeza de la lista, no se puede mover hacia atrás
            if (currentNode == head) {
                break;
            }
            nextNode = currentNode.getNext();
            currentNode.setNext(prevNode);
            currentNode.setPrev(nextNode);
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
        Node newPrevNode = currentNode.getPrev();
        while (newCurrentNode.getNext() != currentNode) {
            newCurrentNode = newCurrentNode.getNext();
        }
        newCurrentNode.setNext(newNextNode);
        newNextNode.setPrev(newCurrentNode);
        newPrevNode.setNext(newCurrentNode);
        newCurrentNode.setPrev(newPrevNode);
    }
    //9. Obtener un informe de niños por rango de edades

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
                kidDTO.setName(kid.getName());
                kidDTO.setAge(kid.getAge());
                kidDTO.setCodeLocation(kid.getLocation().getCode());
                kidsInRange[i++] = kidDTO;
            }
            current = current.getNext();
        }

        return kidsInRange;
    }

    //10. Implementar un método que me permita enviar al final de la lista a los niños que su nombre inicie con una letra dada


    public boolean isEmpty() {
        return tail == null;
    }

    public boolean moveToEndByInitial(String initial) throws ListDEException {
        if (isEmpty()) {
            throw new ListDEException("La lista esta vacia");
        }

        boolean moved = false; // se inicializa en false
        Node current = head;
        Node previous = null;
        while (current != null) {
            Kid kid = current.getData();
            if (kid.getName().startsWith(initial)) {
                if (previous == null) {
                    head = current.getNext();
                    if (head != null) {
                        head.setPrev(null);
                    }
                } else {
                    previous.setNext(current.getNext());
                    if (current.getNext() != null) {
                        current.getNext().setPrev(previous);
                    }
                }
                if (current.getNext() == null) {
                    tail = previous;
                }
                tail.setNext(current);
                current.setPrev(tail);
                current.setNext(null);
                current = previous.getNext();

                moved = true; // se actualiza a true si se mueve al menos un nodo
            } else {
                previous = current;
                current = current.getNext();
            }
        }

        return moved; // se retorna el valor booleano actualizado
    }


    //Sustentación 8/05/2023

    /*Algoritmo metodo kamikc
    Si
       la lista esta vacia
               no hacemos nada

    Si
      La posicion es 0, actualizamos la cabeza para pasarse al primer nodo

    Si
      La posicion 0 es la unica que hay en la lista validamos que no tenga nodo anterior de lo contrario nos revienta el programa

    Si
     la posicion es igual o mayor al tamaño de la lista

            no hay nodos para eliminar

            Recorremos la lista hasta llegar al nodo que vamos a eliminar ,llevando el puntero al nodo anterior

     Actualizo el puntero del nodo anterior para saltar el nodo que voy a eliminar

        *es decir poner el siguiente nodo como el siguiente del nodo que voy a eliminar*

            suelto el nodo que voy a eliminar

            actualizo la lista si es necesario


     */
    public void removeKamicase(int index) {

        if (this.head == null) {
            // La lista está vacía, no hay nada que hacer
            return;
        }

        if (index == 0) {
            // La posición es 0, actualizar la cabeza de la lista para saltar el primer nodo
            this.head = this.head.getNext();

            if (this.head != null) {
                this.head.setPrev(null);
            }
            this.size--;
            return;
        }

        if (index >= this.size) {
            // La posición es mayor o igual al tamaño de la lista, no hay un nodo para eliminar en esa posición
            return;
        }

        Node current = this.head;
        int i = 0;

        while (current != null && i < index) {
            current = current.getNext();
            i++;
        }

        // Actualizar el puntero del nodo anterior para saltar el nodo que se va a eliminar

        current.getPrev().setNext(current.getNext());
        if (current.getNext() != null) {
            current.getNext().setPrev(current.getPrev());
        }

        // Liberar el nodo que se está eliminando
        current = null;
        this.size--;
    }

}




