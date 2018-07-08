//package

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    //constructor init
    private boolean [][] site_status;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf_bk;
    private int N;
    private  int openSitesNum;
    public Percolation(int n)       {
        if (n <= 0) throw new IllegalArgumentException("N is illegal");
        this.N = n;
        this.openSitesNum = 0;
        site_status = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N*N+2);
        uf_bk = new WeightedQuickUnionUF(N*N+2);

        //uf.union();
        for(int i = 0;i < N;i++){
            for(int j = 0;j < N;j++){
                site_status[i][j] = false;
            }
        }
        //uf.connected()

    }         // create n-by-n grid, with all sites blocked
    public void open(int row, int col) {
        if(row < 1||row > N||col < 1||col > N) throw
                new IllegalArgumentException("row or col out of bounds is "+row+col);


        if(!site_status[row-1][col-1]) {
            openSitesNum++;
            site_status[row-1][col-1] = true;
            int pos = (row-1)*N +col;
            if(row == 1) {
               uf.union(pos, 0);
               uf_bk.union(pos,0);
            }
            if(row > 1 && site_status[row - 2][col - 1]){   //
                uf.union(pos,pos - N);
                uf_bk.union(pos,pos - N);
            }
            if(row < N && site_status[row][col - 1]){//
                uf.union(pos,pos + N);
                uf_bk.union(pos,pos + N);
            }

            if(col > 1&& site_status[row - 1][col - 2]){//&&!site_status[row - 1][col - 2]
                uf.union(pos,pos - 1);
                uf_bk.union(pos,pos - 1);
            }
            if(col < N&& site_status[row - 1][col]){//&&!site_status[row - 1][col]
                uf.union(pos,pos + 1);
                uf_bk.union(pos,pos + 1);
            }
            if(row == N){
            //    uf.union(pos,N*N+1);
                uf_bk.union(pos,N*N+1);
            }
        }
    }   // open site (row, col) if it is not open already
    public boolean isOpen(int row, int col){
        if(row < 1||row > N||col < 1||col > N) throw
                new IllegalArgumentException("row or col out of bounds");
        return site_status[row - 1][col - 1];
    }  // is site (row, col) open?
    public boolean isFull(int row, int col){
        if(row < 1||row > N||col < 1||col > N) throw
                new IllegalArgumentException("row or col out of bounds");
        return uf.connected((row-1)*N + col,0);
    }  // is site (row, col) full?
    public int numberOfOpenSites(){

        return this.openSitesNum;
    }       // number of open sites
    public boolean percolates()    {
        return uf_bk.connected(0,N*N + 1);
    }          // does the system percolate?

    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        percolation.open(1,1);
        StdOut.print(percolation.percolates());
    }  // test client (optional)
}
