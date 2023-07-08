package typecheck.Types;

public interface Scope {
    String name();
    String typeName();
    void nameAdd(String s);
}
