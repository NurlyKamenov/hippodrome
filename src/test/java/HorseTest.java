import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;


class HorseTest {
    @Test
    void nullIllegalArgumentException(){
        IllegalArgumentException thrown=assertThrows(IllegalArgumentException.class, () -> new Horse(null,1,1));
        assertEquals("Name cannot be null.",thrown.getMessage());
    }
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n","\t","\r"})
    void emptyAndSpaceArgumentException(String name){
        IllegalArgumentException thrown=assertThrows(IllegalArgumentException.class, () -> new Horse(name,1,1));
        assertEquals("Name cannot be blank.",thrown.getMessage());
    }

    @Test
    void speedIllegalArgumentException(){
        IllegalArgumentException thrown=assertThrows(IllegalArgumentException.class, () -> new Horse("null",-1,1));
        assertEquals("Speed cannot be negative.",thrown.getMessage());
    }

    @Test
    void distanceIllegalArgumentException(){
        IllegalArgumentException thrown=assertThrows(IllegalArgumentException.class, () -> new Horse("null",1,-1));
        assertEquals("Distance cannot be negative.",thrown.getMessage());
    }
    @Test
    void getName() throws NoSuchFieldException, IllegalAccessException {
        String expectedName="Horse";
        String fieldName = "name";
        Horse horse=new Horse(expectedName,1,1);
        Field field=horse.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        String actualName = (String) field.get(horse);
        assertEquals(expectedName,actualName);
    }

    @Test
    void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        double expectedSpeed=1;
        String fieldName = "speed";
        Horse horse=new Horse("Horse",expectedSpeed,1);
        Field field=horse.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        double actualSpeed= (double) field.get(horse);
        assertEquals(expectedSpeed,actualSpeed);
    }

    @Test
    void getDistance() throws NoSuchFieldException, IllegalAccessException {
        double expectedDistance=1;
        String fieldName = "distance";
        Horse horse=new Horse("Horse",1,expectedDistance);
        Field field=horse.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        double actualDistance = (double) field.get(horse);
        assertEquals(expectedDistance,actualDistance);
    }
    @Test
    void getDistanceByDefault() throws NoSuchFieldException, IllegalAccessException {
        double expectedDistance=0;
        String fieldName = "distance";
        Horse horse=new Horse("Horse",1);
        Field field=horse.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        double actualDistance = (double) field.get(horse);
        assertEquals(expectedDistance,actualDistance);
    }

    @Test
    void moveTestUseRandom(){
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
            new Horse("testHorse",3,3).move();
            mockedStatic.verify(()->Horse.getRandomDouble(0.2,0.9));
        }
    }
    @ParameterizedTest
    @CsvSource({"horse1,1,1",
                "horse2,2,2",
                "horse3,3,3"})
    void moveTestUseDistance(String name,double speed,double distance) throws NoSuchFieldException, IllegalAccessException {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)){
            Horse horse = new Horse(name,speed,distance);

            mockedStatic.when(()->Horse.getRandomDouble(anyDouble(),anyDouble())).thenReturn(1d);

            horse.move();

            Field field=horse.getClass().getDeclaredField("distance");
            field.setAccessible(true);
            double actualDistance= (double) field.get(horse);
            double expectedDistance = distance + speed * Horse.getRandomDouble(0.2, 0.9);
            assertEquals(expectedDistance,actualDistance);
        }
    }
}