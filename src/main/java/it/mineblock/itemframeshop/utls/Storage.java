package it.mineblock.itemframeshop.utls;

import java.util.ArrayList;
import java.util.List;

public class Storage<K, V> {
    private List<K> key;
    private List<V> value;
    private int index;


    public Storage() {
        key = new ArrayList<>();
        value = new ArrayList<>();
        index = 0;
    }

    public K getKey(int index){
        return this.key.get(index);
    }

    public List<K> getKeys(){
        return this.key;
    }

    public List<K> getKeys(V value){
        List<K> keys = new ArrayList<>();
        for(int i = 0; i < this.index; i++){
            if(value.equals(this.getValue(i))){
                keys.add(getKey(i));
            }
        }
        return keys;
    }

    public K getKey(V value){
        for(int i = 0; i < index; i++){
            if(value.equals(this.getValue(i))){
                return getKey(i);
            }
        }
        return null;
    }

    public Integer getKeyPos(K key){
        for(int i = 0; i < index; i++){
            if(getKey(i).equals(key)){
                return i;
            }
        }
        return null;
    }

    public V getValue(int index){
        return this.value.get(index);
    }

    public List<V> getValues(){
        return this.value;
    }

    public V getValue(K key){
        for(int i = 0; i < index; i++){
            if(key.equals(this.getKey(i))){
                return getValue(i);
            }
        }
        return null;
    }

    public int getLenght() {
        return this.index;
    }

    public void put(K key, V value){
        /*if(containsKey(key)){
            int index = getKeyPos(key);
            this.key[index] = key;
            this.value[index] = value;
        } else {*/
        this.key.add(key);
        this.value.add(value);

        index++;
        //}
    }

    public void remove(K key, V value){
        if(this.key.indexOf(key) == this.value.indexOf(value)){
            this.key.remove(key);
            this.value.remove(value);
        }
        this.index--;
    }

    public void remove(K key){
        int index = this.key.indexOf(key);
        this.key.remove(index);
        this.value.remove(index);
        this.index--;
    }

    public void replaceValue(K key, V value, Integer offset){
        if(offset != null && offset <= 0) return;
        for(int i = 0; i < this.index; i++){
            if(this.key.get(i) == key){
                this.value.add(i, value);
                if(offset != null){
                    offset--;
                    if(offset == 0) return;
                }
            }
        }
    }

    public void replaceValue(K key, V value){
        replaceValue(key, value, null);
    }

    public void setValues(List<V> value) {
        this.value = value;
    }

    public void setKeys(List<K> key) {
        this.key = key;
    }

    public boolean equals(Object o) {
        return o == this;
    }

    public boolean containsKey(K key){
        for(int i = 0; i < index; i++){
            if(getKey(i) == key){
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(V value){
        for(int i = 0; i < index; i++){
            if(getValue(i) == value){
                return true;
            }
        }
        return false;
    }
}
