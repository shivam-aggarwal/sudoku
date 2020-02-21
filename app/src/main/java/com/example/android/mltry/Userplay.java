package com.example.android.mltry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Userplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userplay);
        View v = findViewById(R.id.button1);
        RandomSudokuOnSubmit(v);
    }

    public void CheckOnSubmit(View v) {
        TableLayout table = findViewById(R.id.table);
        int[][] mat = new int[9][9];
        for (int i = 0; i < table.getChildCount(); i++) {
            TableRow tableRow = (TableRow) table.getChildAt(i);
            for (int j = 0; j < tableRow.getChildCount(); j++) {
                EditText mEdit = (EditText) tableRow.getChildAt(j);
                String temp = mEdit.getText().toString();
                if (temp.length() == 0) {
                    mat[i][j] = 0;
                    Toast.makeText(this, "PLEASE FILL THE SUDOKU FIRST!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mat[i][j] = Integer.parseInt(temp);
                }
            }
        }
        if (check(mat, 9, 0, 0,3)) {
            Toast.makeText(this, "KUDOS YOU HAVE SOLVED THE SUDOKU!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "CHECK YOUR SUDOKU!", Toast.LENGTH_SHORT).show();
        }
        return;
    }
    // Credits to Shubham Shahi for RandomSudokuOnSubmit fxn
    public void RandomSudokuOnSubmit(View v) {
        List<Integer> list = new ArrayList<>();
        int arr[][] = new int[9][9];
        int ar[] = new int[5];

        for (int i = 0; i < 9; i++) {
            list.add(i + 1);
        }
        // sudoku obj=new sudoku();
        for (int i = 0; i < 9; i++) {
            int a = getRandomElement(list);
            arr[i][a - 1] = i + 1;
        }
        // SOLVE SUDOKU
        solve(arr, 9, 0, 0, 3);
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 5; k++) {
                ar[k] = getRandomElement(list);
            }

            for (int j = 0; j < 5; j++) {
                arr[i][ar[j] - 1] = -1;// deleting value

            }
        }
        //display the matrix now
        display(arr);
        return;
    }

    public void display(int[][] mat) {
        TableLayout table = findViewById(R.id.table);
        for (int i = 0; i < table.getChildCount(); i++) {
            TableRow tableRow = (TableRow) table.getChildAt(i);
            for (int j = 0; j < tableRow.getChildCount(); j++) {
                EditText mEdit = (EditText) tableRow.getChildAt(j);
                if (mat[i][j] > 0) {
                    mEdit.setText(Integer.toString(mat[i][j]));
                } else {
                    mEdit.setText("");
                }
            }
        }
        return;
    }

    public int getRandomElement(List<Integer> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public boolean check(int[][] mat, int n, int r, int c, int k) {
        int arr[] = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            arr[i] = 0;
        }
        // check rows
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                arr[j] = 0;
            }
            for (int j = 0; j < n; j++) {
                if(mat[i][j] == 0)
                    return false;
                arr[mat[i][j]] += 1;
            }
            for (int j = 1; j <= n; j++) {
                if (arr[j] >= 2) {
                    return false;
                }
            }
        }
        // check colums
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                arr[j] = 0;
            }
            for (int j = 0; j < n; j++) {
                arr[mat[j][i]] += 1;
            }
            for (int j = 1; j <= n; j++) {
                if (arr[j] >= 2) {
                    return false;
                }
            }
        }
        // check boxes
        // there will be n boxes
        for (int box = 0; box < n; box++) {
            int sr = (box / k) * k;
            int sc = (box % k) * k;
            ;
            for (int j = 0; j <= n; j++) {
                arr[j] = 0;
            }
            for (int i = sr; i < sr + k; i++) {
                for (int j = sc; j < sc + k; j++) {
                    arr[mat[i][j]] += 1;
                }
            }
            for (int j = 1; j <= n; j++) {
                if (arr[j] >= 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canPlace(int[][] mat, int n, int r, int c, int k, int num) {
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

    public boolean solve(int[][] mat, int n, int r, int c, int k) {
        if (r >= n) {
            return true;
        }
        if (c >= n) {
            return solve(mat, n, r + 1, 0, k);
        }
        if (mat[r][c] != 0) {
            return solve(mat, n, r, c + 1, k);
        }
        for (int i = 1; i <= n; i++) {
            if (canPlace(mat, n, r, c, k, i)) {
                mat[r][c] = i;
                if (solve(mat, n, r, c + 1, k)) {
                    return true;
                }
                mat[r][c] = 0;
            }
        }
        return false;
    }
}
