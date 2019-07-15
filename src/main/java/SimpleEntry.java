import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SimpleEntry<K,V> {
    private K key;
    private V value;
    SimpleEntry<K,V> next;

    public SimpleEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

}
