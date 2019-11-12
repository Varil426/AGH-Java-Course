import static java.lang.Math.sqrt;

public class Matrix {
    double[]data;
    int rows;
    int cols;
    //...
    Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        data = new double[rows*cols];
    }

    Matrix(double[][] d){
        int max = d[0].length;;
        for(int i=1;i<d.length;i++) {
            if(d[i].length > max) {
                max = d[i].length;
            }
        }
        this.rows = d.length;
        this.cols = max;
        this.data = new double[max*d.length];
        for(int i=0;i<d.length;i++) {
            int j=0;
            while(j<d[i].length) {
                this.data[max*i+j]=d[i][j];
                j++;
            }
            while(j<max) {
                this.data[max*i+j] = 0;
                j++;
            }
        }
    }

    double[][] asArray() {
        double[][] answer = new double[this.rows][this.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                answer[i][j] = this.data[i*this.rows + j];
            }
        }
        return answer;
    }

    double get(int r,int c) {
        return this.data[r*this.cols+c];
    }
    void set (int r,int c, double value){
        this.data[r*this.cols + c]=value;
    }

    public String toString(){
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for(int i=0;i<this.rows;i++){
            buf.append("[");
            for (int j = 0; j < this.cols; j++) {
                buf.append(this.data[i*this.rows + j]);
                if(j != this.cols-1){
                    buf.append(", ");
                }
                else {
                    buf.append("]");
                }
            }
            if(i != this.rows-1){
                buf.append(", ");
            }
            else {
                buf.append("]");
            }
        }
        return buf.toString();
    }

    void reshape(int newRows,int newCols){
        if(rows*cols != newRows*newCols)
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d",rows,cols,newRows,newCols));
        this.rows = newRows;
        this.cols = newCols;
    }

    int[] shape() {
        return new int[] {this.rows, this.cols};
    }

    Matrix add(Matrix m) {
        if(this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException(String.format("Can not add"));
        }
        Matrix answer = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                        answer.set(i,j,this.get(i,j) + m.get(i,j));
            }
        }
        return answer;
    }

    Matrix sub(Matrix m) {
        if(this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException(String.format("Can not subtract"));
        }
        Matrix answer = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                answer.set(i,j,this.get(i,j) - m.get(i,j));
            }
        }
        return answer;
    }

    Matrix mul(Matrix m) {
        if(this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException(String.format("Can not multiply"));
        }
        Matrix answer = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                answer.set(i,j,this.get(i,j) * m.get(i,j));
            }
        }
        return answer;
    }

    Matrix div(Matrix m) {
        if(this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException(String.format("Can not divide"));
        }
        Matrix answer = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if(m.get(i,j) == 0) {
                    throw new RuntimeException(String.format("Division by 0"));
                }
                answer.set(i,j,this.get(i,j) / m.get(i,j));
            }
        }
        return answer;
    }

    Matrix add(double w){
        Matrix answer = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                answer.set(i,j,this.get(i,j) + w);
            }
        }
        return answer;
    }
    Matrix sub(double w){
        Matrix answer = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                answer.set(i,j,this.get(i,j) - w);
            }
        }
        return answer;
    }
    Matrix mul(double w){
        Matrix answer = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                answer.set(i,j,this.get(i,j) * w);
            }
        }
        return answer;
    }
    Matrix div(double w){
        Matrix answer = new Matrix(this.rows, this.cols);
        if(w == 0) {
            throw new RuntimeException("Division by 0");
        }
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                answer.set(i,j,this.get(i,j) / w);
            }
        }
        return answer;
    }

    Matrix dot(Matrix m) {
        if(this.cols != m.rows) {
            throw new RuntimeException(String.format("This can not be done"));
        }
        Matrix answer = new Matrix(this.rows, m.cols);
        for (int i = 0; i < answer.rows; i++) {
            for (int j = 0; j < answer.cols; j++) {
                double insert = 0;
                for (int k = 0; k < this.cols; k++) {
                    insert += this.data[i*this.cols+k] * m.data[k*m.cols+j];
                }
                answer.set(i,j,insert);
            }
        }
        return answer;
    }

    double frobenius() {
        double answer = 0;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                answer += this.get(i,j)*this.get(i,j);
            }
        }
        return sqrt(answer);
    }
    //Kartkówka
    Matrix sumRows() {
        Matrix m = new Matrix(1, this.cols);
        for (int i = 0; i < this.cols; i++) {
            double sum = 0;
            for (int j = 0; j < this.rows; j++) {
                sum += this.get(j,i);
            }
            //0 ponieważ indexuje miejsca w macierzy od 0
            m.set(0,i,sum);
        }
        return m;
    }
}