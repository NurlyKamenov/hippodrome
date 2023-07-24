import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HippodromeTest {
    @Test
    void constNullArgumentException(){
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,()-> new Hippodrome(null));
        assertEquals("Horses cannot be null.", thrown.getMessage());
    }

    @Test
    void constEmptyArgumentException(){
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<Horse>()));
        assertEquals("Horses cannot be empty.", thrown.getMessage());
    }
    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<Horse>();
        for (int i=1;i<=30;i++){
            horses.add(new Horse("horse"+i,i,i));
        }

        Hippodrome hippodrome= new Hippodrome(horses);

        assertEquals(horses,hippodrome.getHorses());
    }

    @Test
    void move() {
        List<Horse> horses = new ArrayList<Horse>();
        for (int i = 1; i<=50; i++){
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse:horses){
            verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        Horse horse1 = new Horse("Horse1",1,1);
        Horse horse2 = new Horse("Horse2",2,2);
        Horse horse3 = new Horse("Horse3",3,3);
        Horse horse4 = new Horse("Horse4",4,4);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1,horse2,horse3,horse4));
        assertSame(horse4,hippodrome.getWinner());
    }
}