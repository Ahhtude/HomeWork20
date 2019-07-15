public class Demo {
    public static void main(String[] args) {
    SimpleHashMap<String, Integer> map = new SimpleHashMap<>();
        map.add("1",1);
        map.add("2",2);
        map.entrySet().stream().forEach(a->
                System.out.println(a.toString()));
        map.keySet().stream().forEach(a->{
            System.out.println(a.toString());
        });
    }

}
