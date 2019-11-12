import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class MatrixTest {

    @Test
    public void testMatrixRowsCols(){
        Random rand = new Random();
        int a = rand.nextInt(5);
        int b = rand.nextInt(5);
        Matrix testing = new Matrix(a,b);
        assertEquals(a,testing.rows);
        assertEquals(b,testing.cols);
    }

    @Test
    public void testMatrixDouble() {
        Matrix expected = new Matrix(new double[][]{{1,2},{4}});
        assertEquals(expected.get(1,1),0.0,0.0);
        assertEquals(expected.rows, 2);
        assertEquals(expected.cols,2);
    }

    @Test
    public void asArray() {
        Matrix testing = new Matrix(new double[][] {{1,2,3},{1,5},{1}});
        assertEquals(testing.asArray().length,testing.rows);
        assertEquals(testing.asArray()[0].length,testing.cols);
    }

    @Test
    public void get() {
        Matrix testing = new Matrix(new double[][] {{1,2,3},{1,5},{1}});
        assertEquals(testing.get(1,1),5,0.0);
        assertEquals(testing.get(2,2),0,0.0);
    }

    @Test
    public void set() {
        Random rand = new Random();
        double r = rand.nextDouble();
        Matrix testing = new Matrix(new double[][] {{1,2,3},{1,5},{1}});
        testing.set(testing.rows-1,testing.cols-1,r);
        assertEquals(testing.get(testing.rows-1,testing.cols-1),r,0.0);
    }

    @Test
    public void testToString() {
        Matrix testing = new Matrix(new double[][] {{1,2,3},{1,5},{1}});
        String s = testing.toString();
        int commas = 0;
        int brackets = 0;
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ',') commas++;
            if(s.charAt(i) == '[' || s.charAt(i) == ']') brackets++;
        }
        assertEquals(8, commas);
        assertEquals(8, brackets);
    }

    @Test
    public void reshape() {
        Matrix testing = new Matrix(new double[][] {{1,2},{3,4}});
        testing.reshape(1,4);
        testing.reshape(4,1);
        try{
            testing.reshape(2,8);
        } catch (Exception e) {
            return;
        }
        fail("No exception");
    }

    @Test
    public void shape() {
        Matrix testing = new Matrix(new double[][] {{1,2},{3,4}});
        assertEquals(testing.shape().length,2);
        assertEquals(testing.shape()[0],testing.rows);
        assertEquals(testing.shape()[1],testing.cols);
    }

    @Test
    public void add() {
        Matrix testing = new Matrix(new double[][] {{1,2},{3,4}});
        Matrix testing1 = testing.add(-1);
        for (int i = 0; i < testing.rows; i++) {
            for (int j = 0; j < testing.cols; j++) {
                assertEquals(testing.get(i,j)-1, testing1.get(i,j),0.0);
            }
        }
    }

    @Test
    public void sub() {
        Matrix testing = new Matrix(new double[][] {{1,2},{3,4}});
        Matrix testing1 = testing.sub(-1);
        for (int i = 0; i < testing.rows; i++) {
            for (int j = 0; j < testing.cols; j++) {
                assertEquals(testing.get(i,j)+1, testing1.get(i,j),0.0);
            }
        }
    }

    @Test
    public void mul() {
        Matrix testing = new Matrix(new double[][] {{1,2},{3,4}});
        Matrix testing1 = testing.mul(-1);
        testing = testing.add(testing1);
        testing1 = testing1.mul(0);
        for (int i = 0; i < testing.rows; i++) {
            for (int j = 0; j < testing.cols; j++) {
                assertEquals(testing.get(i,j), 0,0.0);
                assertEquals(testing1.get(i,j), 0,0.0);
            }
        }
    }

    @Test
    public void div() {
        Matrix testing = new Matrix(new double[][] {{1,2},{3,4}});
        try{
            testing.div(0);
        } catch (Exception e){
            return;
        }
        fail("No exception");
    }

    @Test
    public void testAdd() {
        Matrix testing = new Matrix(new double[][] {{1,2},{3,4}});
        Matrix testing1 = testing.add(testing);
        for (int i = 0; i < testing1.rows; i++) {
            for (int j = 0; j < testing1.cols; j++) {
                assertEquals(testing.get(i,j)*2,testing1.get(i,j),0.0);
            }
        }
    }

    @Test
    public void testSub() {
        Matrix testing = new Matrix(new double[][] {{1,2},{3,4}});
        Matrix testing1 = testing.sub(testing);
        for (int i = 0; i < testing1.rows; i++) {
            for (int j = 0; j < testing1.cols; j++) {
                assertEquals(0,testing1.get(i,j),0.0);
            }
        }
    }

    @Test
    public void testMul() {
        Matrix testing = new Matrix(new double[][] {{1,2},{3,4}});
        Matrix testing1 = testing.mul(-1);
        testing = testing.add(testing1);
        for (int i = 0; i < testing.rows; i++) {
            for (int j = 0; j < testing.cols; j++) {
                assertEquals(0,testing.get(i,j),0.0);
            }
        }
    }

    @Test
    public void testDiv() {
        Matrix testing = new Matrix(new double[][] {{1,2},{3,4}});
        testing = testing.div(testing);
        for (int i = 0; i < testing.rows; i++) {
            for (int j = 0; j < testing.cols; j++) {
                assertEquals(testing.get(i,j),1,0.0);
            }
        }
    }

    @Test
    public void dot() {
        Matrix testing = new Matrix(new double[][] {{1,2},{3,4}});
        Matrix testing1 = new Matrix(new double[][] {{2,2},{2,2}});
        Matrix testing2 = new Matrix(new double[][] {{1}});
        Matrix result = testing.dot(testing1);
        assertEquals("[[6.0,6.0],[14.0,14.0]]",result.toString().replaceAll(" ",""));
        try {
            Matrix result1 = testing.dot(testing2);
        } catch(Exception e) {
            return;
        }
        fail();
    }

    @Test
    public void frobenius() {
        Matrix testing = new Matrix(new double[][] {{1,2,3},{3,4}});
        assertEquals(6.2449979983984, testing.frobenius(), 0.001);
    }

    @Test
    public void sumRows() {
        Matrix m = new Matrix(new double[][]{{1,2,3},{4,5,6},{7,8,9}});
        Matrix row = m.sumRows();
        assertEquals("[[12.0, 15.0, 18.0]]", row.toString());
    }
}