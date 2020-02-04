import org.junit.Test;

import static org.junit.Assert.*;

public class CompositionTest {
    @Test
    void childHasBeenUpdated() {
        Child child = new Child("Zenith");
        Parent parent = new Parent(123, child);
        assertEquals("Zenith", child.getName());
        System.out.println(child);
        System.out.println(parent);
        assertEquals(child.getName(), parent.getChildName());
        child.setName("Zeta");
        assertEquals("Zeta", child.getName());
        assertEquals(child.getName(), parent.getChildName());
        System.out.println(child);
        System.out.println(parent);
    }
}

class Parent {
    private int data;
    private Child child;

    public Parent(int data, Child child) {
        this.data = data;
        this.child = child;
    }

    public String getChildName() {
        return child.getName();
    }

    @Override
    public String toString() {
        return "Parent { data = " + data + ", child = " + child + " }";
    }
}

class Child {
    private String name;

    public Child(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Child { name = " + name + " }";
    }
}
