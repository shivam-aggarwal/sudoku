package com.example.android.mltry;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class Usersolve extends AppCompatActivity {
    int[][] mat = new int[9][9];
    int turn =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersolve);
    }
    public boolean check(int n,int r,int c,int k){
        int arr[] = new int[n+1];
        for(int i=0;i<=n;i++){
            arr[i] = 0;
        }
        //check rows
        for(int i=0;i<n;i++){
            for(int j=0;j<=n;j++){
                arr[j] = 0;
            }
            for(int j=0;j<n;j++){
                arr[mat[i][j]]+=1;
            }
            for(int j=1;j<=n;j++){
                if(arr[j] >= 2){
                    return false;
                }
            }
        }
        // check colums
        for(int i=0;i<n;i++){
            for(int j=0;j<=n;j++){
                arr[j] = 0;
            }
            for(int j=0;j<n;j++){
                arr[mat[j][i]]+=1;
            }
            for(int j=1;j<=n;j++){
                if(arr[j] >= 2){
                    return false;
                }
            }
        }
        // check boxes
        // there will be n boxes
        for(int box=0;box<n;box++){
            int sr = (box/k)*k;
            int sc = (box%k)*k;;
            for(int j=0;j<=n;j++){
                arr[j] = 0;
            }
            for(int i=sr;i<sr+k;i++){
                for(int j=sc;j<sc+k;j++){
                    arr[mat[i][j]]+=1;
                }
            }
            for(int j=1;j<=n;j++){
                if(arr[j] >= 2){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canPlace(int n, int r, int c, int k, int num) {
        for (int i = 0; i < n; i++) {
            if (mat[i][c] == num) {
                return false;
            }
        }
        for (int j = 0; j < n; j++) {
            if (mat[r][j] == num) {
                return false;
            }
        }
        int sr = r / k;
        int sc = c / k;
        sc = sc * k;
        sr = sr * k;
        for (int i = sr; i < sr + k; i++) {
            for (int j = sc; j < sc + k; j++) {
                if (mat[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean solve(int n, int r, int c, int k) {
        if (r >= n) {
            return true;
        }
        if (c >= n) {
            return solve(n, r + 1, 0, k);
        }
        if (mat[r][c] != 0) {
            return solve(n, r, c + 1, k);
        }
        for (int i = 1; i <= n; i++) {
            if (canPlace(n, r, c, k, i)) {
                mat[r][c] = i;
                if (solve(n, r, c + 1, k)) {
                    return true;
                }
                mat[r][c] = 0;
            }
        }
        return false;
    }
    public void submit(View v) {
        if (turn % 2 == 0) {
            turn = 1;
            // change the text on submit button
            Button button = (Button) v;
            ((Button) v).setText("RESET");
            v.setBackgroundColor(getColor(R.color.blue));
            TableLayout table = findViewById(R.id.table);
            for (int i = 0; i < table.getChildCount(); i++) {
                TableRow tableRow = (TableRow) table.getChildAt(i);
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    EditText mEdit = (EditText) tableRow.getChildAt(j);
                    String temp = mEdit.getText().toString();
                    if (temp.length() == 0) {
                        mat[i][j] = 0;
                    } else {
                        mat[i][j] = Integer.parseInt(temp);
                    }
                }
            }
            if (check(9, 0, 0, 3)) {
                if (!solve(9, 0, 0, 3)) {
                    Toast.makeText(this, "THIS SUDOKU CAN NOT BE SOLVED!", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(this, "PLEASE ENTER CORRECT SUDOKU!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "THIS SUDOKU CAN BE SOLVED!", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < table.getChildCount(); i++) {
                TableRow tableRow = (TableRow) table.getChildAt(i);
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    EditText mEdit = (EditText) tableRow.getChildAt(j);
                    mEdit.setText(Integer.toString(mat[i][j]));
                }
            }
        }
        else{
            turn =0;
            //change the name on submit button
            Button button = (Button) v;
            v.setBackgroundColor(getColor(R.color.darkred));
            ((Button) v).setText("SOLVE");
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    mat[i][j] = 0;
                }
            }
            // change the view
            TableLayout table = findViewById(R.id.table);
            for (int i = 0; i < table.getChildCount(); i++) {
                TableRow tableRow = (TableRow) table.getChildAt(i);
                for (int j = 0; j < tableRow.getChildCount(); j++) {
                    EditText mEdit = (EditText) tableRow.getChildAt(j);
                    mEdit.setText("");
                }
            }
            //return
            return;
        }
    }


}
