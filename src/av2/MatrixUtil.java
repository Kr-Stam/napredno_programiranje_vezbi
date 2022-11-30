package av2;

public class MatrixUtil {

    public static double sum(double[][] a){
        int sum, n, m;
        sum = 0;
        n = a.length;
        m = a[0].length;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                sum += a[i][j];
            }
        }
        return sum;
    }

    public static double average(double[][] a){
        int n = a.length;
        int m = a[0].length;
        return sum(a)/(n*m);
    }
}
