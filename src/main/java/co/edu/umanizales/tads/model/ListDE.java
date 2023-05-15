package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ListDE {
    private NodeDE headDE;
    private NodeDE tail;
    private int size;

    private List<Pet> pets = new ArrayList<>();

    public void addPets(Pet pet) throws ListDEException {
        if (headDE != null) {
            NodeDE temp = headDE;
            while (temp.getNextDE() != null) {
                if (temp.getData().getPetIdentification().equals(pet.getPetIdentification())) {
                    throw new ListDEException("Ya existe la mascota");
                }
                temp = temp.getNextDE();
            }
            if (temp.getData().getPetIdentification().equals(pet.getPetIdentification())) {
                throw new ListDEException("Ya existe la mascota");
            }
            /// Parado en el último
            NodeDE newNode = new NodeDE(pet);
            temp.setNextDE(newNode);
            newNode.setPrevious(temp);

        } else {
            headDE = new NodeDE(pet);
        }
        size++;
    }

    public List<Pet> print() {
        pets.clear();
        if (headDE != null) {
            NodeDE temp = headDE;
            while (temp != null) {
                pets.add(temp.getData());
                temp = temp.getNextDE();
            }
        }
        return pets;
    }

    public void addPetToStart(Pet pet) throws ListDEException {
        if (pet == null) {
            throw new ListDEException("El objeto pet no puede ser nulo");
        }

        NodeDE nodeCP = new NodeDE(pet);
        if (headDE != null) {
            headDE.setPrevious(nodeCP);
            nodeCP.setNextDE(headDE);
        }
        headDE = nodeCP;
        size++;
    }


    public void addPetByPosition(Pet pet, int position) throws ListDEException {
        if (position < 0 || position > size) {
            throw new ListDEException("Posicion invalida:" + position);
        }
        NodeDE newNode = new NodeDE(pet);
        if (position == 0) {
            newNode.setNextDE(headDE);
            if (headDE != null) {
                headDE.setPrevious(newNode);
            }
            headDE = newNode;
        } else {
            NodeDE current = headDE;
            for (int i = 1; i < position - 1; i++) {
                current = current.getNextDE();
            }
            newNode.setNextDE(current.getNextDE());
            if (current.getNextDE() != null) {
                current.getNextDE().setPrevious(newNode);
            }
            current.setNextDE(newNode);
            newNode.setPrevious(current);
        }
    }
    public void deletePetById(String id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("El identificador del dueño no puede ser nulo");
        }

        NodeDE temp = headDE;
        while (temp != null) {
            if (temp.getData().getPetIdentification().equals(id)) {
                NodeDE prev = temp.getPrevious();
                NodeDE next = temp.getNextDE();
                if (prev == null) {
                    headDE = next;
                } else {
                    prev.setNextDE(next);
                }
                if (next != null) {
                    next.setPrevious(prev);
                }
            }
            temp = temp.getNextDE();
        }
    }


    //1. invertir lista

    public void invertPetsList() throws ListDEException {
        if (this.headDE == null) {
            throw new ListDEException("No se puede invertir la lista porque esta vacia");
        }

        ListDE listcopy = new ListDE();
        NodeDE temp = this.headDE;
        while (temp != null) {
            listcopy.addPetToStart(temp.getData());
            temp = temp.getNextDE();
        }
        this.headDE = listcopy.getHeadDE();
    }

    //2. Niños al inicio y niñas al final
    public void orderMasculinePetsToStart() throws ListDEException {
        if (headDE == null) {
            throw new ListDEException(
                    "La lista está vacía y no se pueden agregar elementos");
        }
        ListDE listCopy = new ListDE();
        NodeDE temp = headDE;
        while (temp != null) {
            if (temp.getData().getPetGender() == 'M') {
                listCopy.addPetToStart(temp.getData());
            } else {
                listCopy.addPets(temp.getData());
            }
            temp = temp.getNextDE();
        }
        headDE = listCopy.getHeadDE();
    }

    //3. Intercalar niño, niña, niño, niña
    public void intertervalePets() throws ListDEException {
        if (headDE == null) {
            throw new ListDEException("La lista está vacía y no se puede realizar el sorteo");
        }

        ListDE listM = new ListDE();
        ListDE listF = new ListDE();
        NodeDE temp = this.headDE;
        while (temp != null) {
            if (temp.getData().getPetGender() == 'M') {
                listM.addPets(temp.getData());
            }
            if (temp.getData().getPetGender() == 'F') {
                listF.addPets(temp.getData());
            }
            temp = temp.getNextDE();
        }

        if (listM.getSize() == 0 || listF.getSize() == 0) {
            throw new ListDEException(
                    "No hay suficientes mascotas de ambos géneros para realizar el sorteo");
        }

        ListDE ListCP = new ListDE();
        NodeDE Nodem = listM.getHeadDE();
        NodeDE Nodef = listF.getHeadDE();
        while (Nodem != null || Nodef != null) {
            if (Nodem != null) {
                ListCP.addPets(Nodem.getData());
                Nodem = Nodem.getNextDE();
            }
            if (Nodef != null) {
                ListCP.addPets(Nodef.getData());
                Nodef = Nodef.getNextDE();
            }
        }
        this.headDE = ListCP.getHeadDE();
    }

    //4. Dada una edad eliminar a los niños de la edad dada
    public void deletePetsByAge(Byte age) throws ListDEException {
        if (age == null) {
            throw new ListDEException("La edad de la mascota no puede ser nula");
        }
        NodeDE temp = headDE;
        while (temp != null) {
            if (temp.getData().getAgePet() == age) {
                NodeDE prev = temp.getPrevious();
                NodeDE next = temp.getNextDE();
                if (prev == null) {
                    headDE = next;
                } else {
                    prev.setNextDE(next);
                }
                if (next != null) {
                    next.setPrevious(prev);
                }
            }
            temp = temp.getNextDE();
        }
    }

    //5. Obtener el promedio de edad de los niños de la lista

    public float averagePetAge() throws ListDEException {
        if (headDE != null) {
            NodeDE temp = headDE;
            int counter = 0;
            int ages = 0;
            while (temp.getNextDE() != null) {
                counter++;
                ages += temp.getData().getAgePet();
                temp = temp.getNextDE();
            }
            if (counter == 0) {
                throw new ListDEException("No se puede calcular el porcentaje en una lista vacia");
            }
            return (float) ages / counter;
        } else {
            throw new ListDEException("No se puede calcular el porcentaje en una lista vacia");
        }
    }

    //6. Generar un reporte que me diga cuantos niños hay de cada ciudad.    Chinchiná 3, Manizales 4, Pereira 5
    public int getPetsCountByLocationCode(String code) throws ListDEException {
        if (code == null || code.isEmpty()) {
            throw new ListDEException("El codigo de la ciudad no puede estar vacio");
        }
        int count = 0;
        if (this.headDE != null) {
            NodeDE temp = this.headDE;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNextDE();
            }
        }
        return count;
    }

    //7. Método que me permita decirle a un niño determinado que adelante un numero de posiciones dadas

    public void MovePetPosition(String id, int position) throws ListDEException {
        if (position < 0) {
            throw new ListDEException("La posición debe ser un número positivo");
        }
        if (headDE != null) {
            NodeDE temp = headDE;
            int counter = 1;
            while (temp != null && !temp.getData().getPetIdentification().equals(id)) {
                temp = temp.getNextDE();
                counter++;
            }
            if (temp != null) {
                int newPosition = counter - position;
                if (newPosition < 0) {
                    throw new ListDEException(
                            "La posición especificada está fuera de los límites de la lista");
                }
                Pet listCopy = temp.getData();
                deletePetById(temp.getData().getPetIdentification());
                if (newPosition > 0) {
                    addPetByPosition(listCopy, newPosition);
                } else {
                    addPetToStart(listCopy);
                }
            }
        }
    }

    //8. Método que me permita decirle a un niño determinado que pierda un numero de posiciones dadas
    public void losePetPosition(String id, int position) throws ListDEException {

        if (position < 0) {
            throw new ListDEException("La posicion debe ser positiva");
        }
        NodeDE temp = headDE;
        int count = 1;
        while (temp != null && !temp.getData().getPetIdentification().equals(id)) {
            temp = temp.getNextDE();
            count++;
        }

        int sum = position + count;
        Pet listCP = temp.getData();
        deletePetById(temp.getData().getPetIdentification());
        addPetByPosition(listCP, sum);
    }
    //9. Obtener un informe de niños por rango de edades

    public int getPetRangeByAge(int Start, int finish) throws ListDEException {
        if (Start < 0 || finish < 0) {
            throw new ListDEException("El informe de rangos no puede ser negativo");
        }
        NodeDE temp = headDE;
        int counter = 0;
        while (temp != null) {
            if (temp.getData().getAgePet() >= Start && temp.getData().getAgePet() <= finish) {
                counter++;
            }
            temp = temp.getNextDE();
        }
        return counter;
    }

    //10. Implementar un método que me permita enviar al final de la lista a los niños que su nombre inicie con una letra dada

    public void movePetToTheEndByLetter(char letter) throws ListDEException {
        if (this.headDE != null) {
            ListDE listCopy = new ListDE();
            NodeDE temp = this.headDE;
            char firstChar = Character.toUpperCase(letter);

            while (temp != null) {
                char firstLetter = temp.getData().getNamePet().charAt(0);
                if (Character.toUpperCase(firstLetter) != firstChar) {
                    listCopy.addPetToStart(temp.getData());
                } else {
                    listCopy.addPets(temp.getData());
                }
                temp = temp.getNextDE();
            }
            this.headDE = listCopy.getHeadDE();
        } else {
            throw new ListDEException("La lista no puede estar vacia");
        }
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

        if (this.headDE == null) {
            // La lista está vacía, no hay nada que hacer
            return;
        }

        if (index == 0) {
            // La posición es 0, actualizar la cabeza de la lista para saltar el primer nodo
            this.headDE = this.headDE.getNextDE();

            if (this.headDE != null) {
                this.headDE.setPrevious(null);
            }
            this.size--;
            return;
        }

        if (index >= this.size) {
            // La posición es mayor o igual al tamaño de la lista, no hay un nodo para eliminar en esa posición
            return;
        }

        NodeDE current = this.headDE;
        int i = 0;

        while (current != null && i < index) {
            current = current.getNextDE();
            i++;
        }

        // Actualizar el puntero del nodo anterior para saltar el nodo que se va a eliminar

        current.getPrevious().setNextDE(current.getNextDE());
        if (current.getNextDE() != null) {
            current.getNextDE().setPrevious(current.getPrevious());
        }

        // Liberar el nodo que se está eliminando
        current = null;
        this.size--;
    }

}




