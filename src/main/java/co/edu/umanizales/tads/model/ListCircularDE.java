package co.edu.umanizales.tads.model;


import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;

import java.util.Random;

@Data
public class ListCircularDE {
    private NodeDE headCircular;
    private int size;
    private NodeDE tail;




    //añadir perro

/*
Creamos un nuevo nodo llamado newNode con la mascota que queremos agregar.

Verificamos si la lista está vacía.
              Si es así
 asignamos newNode como el primer nodo (headCircular) y configuramos los enlaces nextDE
 y previous de newNode para que apunten a sí mismo.

Si la lista no está vacía pero tiene solo un nodo (headCircular)
    actualizamos los enlaces de headCircular y newNode para que se enlacen entre sí.

Si la lista tiene más de un nodo
    insertamos newNode en medio de la lista. Para ello, obtenemos una referencia al nodo anterior a headCircular (almacenado en laterNode).

Actualizamos los enlaces de headCircular, newNode y laterNode para insertar newNode correctamente en la lista.

Incrementamos el contador de tamaño size en 1 para reflejar que se ha agregado una nueva mascota.
 */
    public void addPet(Pet pet) {
        NodeDE newNode = new NodeDE(pet);
        if (headCircular == null) {
            headCircular = newNode;
            newNode.setNextDE(headCircular);
            newNode.setPrevious(headCircular);
        }
        if (size == 1) {
            headCircular.setPrevious(newNode);
            headCircular.setNextDE(newNode);
            newNode.setNextDE(headCircular);
            newNode.setPrevious(headCircular);
        }
        NodeDE laterNode = headCircular.getPrevious();
        if (size > 1) {
            headCircular.setPrevious(newNode);
            newNode.setNextDE(headCircular);
            newNode.setPrevious(laterNode);
            laterNode.setNextDE(newNode);
        }
        size++;
    }

    public Pet[] listCircular() throws ListDEException {
        Pet[] petList = new Pet[size];
        if (size == 0) {
            return petList;
        }
        int num = 0;
        NodeDE temp = headCircular;

        if (temp == null) {
            throw new ListDEException("La lista está vacía");
        }

        do {
            petList[num] = temp.getData();
            temp = temp.getNextDE();
            num++;
        } while (temp != headCircular);

        return petList;
    }


    //añadir al inicio

    /*
Creamos un nuevo nodo llamado newNode con la mascota que queremos agregar.

Obtenemos una referencia al nodo anterior a headCircular y la almacenamos en later.

Verificamos si la lista está vacía
 Si es así
  llamamos al método addPet(pet) para agregar pet a la lista y luego asignamos headCircular como newNode.

Si la lista no está vacía
   actualizamos los enlaces de los nodos para insertar newNode al inicio de la lista.

El enlace nextDE del nodo later se actualiza para que apunte a newNode.

El enlace previous de headCircular se actualiza para que apunte a newNode.

Los enlaces previous y nextDE de newNode se actualizan para que se enlacen correctamente con later y headCircular.

        Asignamos newNode como el nuevo headCircular.

Incrementamos el contador de tamaño size en 1 para reflejar que se ha agregado una nueva mascota.
     */

    public void addToStart(Pet pet) {
        NodeDE newNode = new NodeDE(pet);
        NodeDE later = headCircular.getPrevious();
        if (headCircular == null) {
            addPet(pet);
            headCircular = newNode;
        } else {
            later.setNextDE(newNode);
            headCircular.setPrevious(newNode);
            newNode.setPrevious(later);
            newNode.setNextDE(headCircular);
            headCircular = newNode;
        }
        size++;
    }


    //añadir por posicion

    /*

Verificamos si la posición es válida.
   Si es negativa o mayor que el tamaño de la lista, lanzamos un mensaje de error.

Creamos un nuevo nodo llamado newNode con la mascota que queremos agregar.

Si la lista tiene elementos:

Si la posición es 1, queremos agregar newNode al inicio de la lista.

Si la lista tiene un solo elemento

   actualizamos los enlaces de headCircular y newNode para que se enlacen entre sí, y newNode se convierte en el nuevo headCircular.

Si la lista tiene más de un elemento

   actualizamos los enlaces de los nodos para insertar newNode al inicio de la lista.

Si la posición no es 1
   queremos agregar newNode en una posición diferente al inicio de la lista.

Iteramos a través de la lista utilizando un contador y una referencia temporal temp para llegar al nodo anterior a la posición deseada.

Actualizamos los enlaces de los nodos para insertar newNode en la posición deseada.

Si la lista está vacía
 lanzamos un mensaje de error indicando que no hay datos.

Incrementamos el tamaño de la lista en 1.
     */


    public void addByPosition(Pet pet, int position) throws ListDEException {
        if (position < 0 || position > size) {
            throw new ListDEException("No se podra añadir la mascota en esa posicion  ");
        }
        NodeDE newNode = new NodeDE(pet);

        if (size > 0) {
            if (position == 1) {
                NodeDE before = headCircular.getPrevious();
                if (size == 1) {
                    headCircular.setPrevious(newNode);
                    headCircular.setNextDE(newNode);
                    newNode.setNextDE(headCircular);
                    newNode.setPrevious(headCircular);
                    headCircular = newNode;
                } else {
                    headCircular.setPrevious(newNode);
                    before.setNextDE(newNode);
                    newNode.setNextDE(headCircular);
                    newNode.setPrevious(before);
                    headCircular = newNode;
                }
            } else {
                NodeDE temp = headCircular;
                int counter = 1;
                while (counter < position - 1) {
                    temp = temp.getNextDE();
                    counter++;
                }
                newNode.setNextDE(temp.getNextDE());
                newNode.setPrevious(temp);
                temp.getNextDE().setPrevious(newNode);
                temp.setNextDE(newNode);
            }
        } else {
            throw new ListDEException("No hay datos");
        }
        size++;
    }

    //Bañar al perro

    /*
Creamos un nodo temporal temp y lo inicializamos como headCircular.

Generamos un número aleatorio entre 0 y el tamaño de la lista

  (inclusive) utilizando la clase Random y lo almacenamos en numRandom.

Si la dirección es 'd' (derecha)
    iteramos hacia adelante desde temp hasta el nodo en la posición aleatoria (numRandom).

Si la dirección es 'i' (izquierda)
    iteramos hacia atrás desde temp hasta el nodo en la posición aleatoria (numRandom).

Obtenemos la mascota (Pet) del nodo temp y la almacenamos en pet.

Si la mascota no ha sido bañada (getPetbathed() devuelve false)

   marcamos la mascota como bañada (setPetbathed(true)) en el nodo temp y devolvemos la mascota.

Si la mascota ya ha sido bañada, devolvemos null.
     */

    public Pet getBathedPets(char direction) {
        NodeDE temp = headCircular;
        Random random = new Random();
        int numRandom = random.nextInt(size + 1);
        if (direction == 'd') {
            for (int i = 1; i < numRandom; i++){
                temp = temp.getNextDE();
            }
        }
        if (direction == 'i') {
            for (int i = 1; i < numRandom; i++){
                temp = temp.getPrevious();
            }
        }
        Pet pet = temp.getData();
        if (pet.getPetbathed() != true){
            temp.getData().setPetbathed(true);
            return pet;
        }
        return null;
    }
}
