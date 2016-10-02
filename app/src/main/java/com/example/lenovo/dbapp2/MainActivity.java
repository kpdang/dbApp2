package com.example.lenovo.dbapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;

/**THERE ARE 2 TEXTFILEDS IN THIS APP.
  FIRST ONE IS TO ENTER ROLL N0. OF THE STUDENT(FOR Q3) AND TO ENTER FILE NAME(FOR Q2)
 FIRST ONE IS TO ENTER NAME OF THE STUDENT(FOR Q3 AND Q1) AND TO ENTER FILE CONTENTS(FOR Q2)

 BUTTONS....
 VIEW INT button is to read a file from internal storage
 SAVE INT button is to save a file to internal storage
 VIEW EXT button is to read a file from external storage
 INSERT button is to insert a record in the database
 UPDATE button is to update a record in the database
 DELETE button is to delete a record from the database
 VIEW button is to view the contents of the student table


I HAVE APPLIED SHARED PREFERENCES ON 2ND TEXT FIELD THAT IS TEXTFIELD TO ENTER NAME

**/

public class MainActivity extends AppCompatActivity {

    EditText e1,e2;
    TextView rd_file,t1,t2;
    //TextView save_rd_file,save_t1,save_t2;
    String save_rd_file,save_t1,save_t2;
    Button view_file;
    Button save_file;

    Button saveex_file;
    Button save_pref;
    private Context context=this;
    public static final String PREFS = "pr";
    SharedPreferences sp;
    int count=0;//variable to keep track of no of times data has been retrieved from database.We need this variable bcoz if screen is already
    //displaying the data and we delete or update or insert data then data which displayed on the screen should be refreshed.
    //if count is greater than 0 then it means data is alrady on the screen and hence we will refresh it when we update or delete data.
    StudentDbHelper db;
    TableLayout tab;
    TextView t;
    TableRow tr;
    Button b1;
    Button b2;
    Button b3;
    Button b4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=(EditText)findViewById(R.id.editText21);
        e2=(EditText)findViewById(R.id.editText22);
        rd_file=(TextView) findViewById(R.id.textView);
        //save_rd_file=(TextView) findViewById(R.id.textView);
        //save_t1=(TextView) findViewById(R.id.textView5);
        //save_t2=(TextView) findViewById(R.id.textView6);
        view_file=(Button)findViewById(R.id.button21);
        save_file=(Button)findViewById(R.id.button22);

        saveex_file=(Button)findViewById(R.id.button24);
        save_pref=(Button)findViewById(R.id.button31);
        t1=(TextView)findViewById(R.id.textView5);
        t2=(TextView)findViewById(R.id.textView6);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b3=(Button)findViewById(R.id.button4);
        db=new StudentDbHelper(getBaseContext(),null,null,1);

    }

    public void save_sharedPref(View view)//this function will be called when we click on SAVE PREF button
    {

        EditText name=(EditText)findViewById(R.id.editText21);
        String save_name=name.getText().toString();
        SharedPreferences sp=getSharedPreferences(PREFS,0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("student_name",save_name);
        editor.commit();
        Toast t=Toast.makeText(getApplicationContext(),"Your file has been Saved using shared preferences", Toast.LENGTH_SHORT);
        t.show();
        name.setText(" ");
    }

    public void view_sharedPref(View view)//this function will be called when we click on VIEW PREF button
    {
        SharedPreferences sp = getSharedPreferences(PREFS,0);
        String name= sp.getString("student_name", null);
        TextView tv=(TextView) findViewById(R.id.textView);
        String result="Name:"+ name;
        tv.setText(result);

    }
    public void viewFile(View view)//this function will be called when we click on VIEW INT button
    {
        EditText f=(EditText)findViewById(R.id.editText22);
        String file_name=f.getText().toString();
        TextView tv=(TextView) findViewById(R.id.textView);

        FileInputStream istream;
        try {
            String file_content;
            istream=openFileInput(file_name);
            InputStreamReader ireader=new InputStreamReader(istream);
            BufferedReader bufferedReader = new BufferedReader(ireader);
            StringBuffer stringbuffer=new StringBuffer();
            while ((file_content = bufferedReader.readLine()) != null) {
                stringbuffer.append(file_content);

            }
            tv.setText(stringbuffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void saveFile(View view)//this function will be called when we click on SAVE INT button
    {

        EditText r=(EditText)findViewById(R.id.editText21);
        String file_content=r.getText().toString();
        EditText f=(EditText)findViewById(R.id.editText22);
        String file_name=f.getText().toString();

        FileOutputStream ostream;
        try {
            ostream = openFileOutput(file_name, Context.MODE_PRIVATE);
            ostream.write(file_content.getBytes());
            ostream.close();
            Toast t=Toast.makeText(getApplicationContext(),"Your file has been Saved", Toast.LENGTH_SHORT);
            t.show();
            r.setText(" ");
            f.setText(" ");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveexFile(View view)//this function will be called when we click on SAVE EXT button
    {
        String space_avail = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(space_avail)) {
            EditText f=(EditText)findViewById(R.id.editText22);
            String file_name=f.getText().toString();
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), file_name);
            EditText r=(EditText)findViewById(R.id.editText21);
            String file_content=r.getText().toString();
            FileOutputStream ostream;

            try {
                ostream = openFileOutput(file_name, Context.MODE_PRIVATE);
                ostream.write(file_content.getBytes());
                ostream.close();
                Toast t=Toast.makeText(getApplicationContext(),"Your file has been Saved", Toast.LENGTH_SHORT);
                t.show();
                r.setText(" ");
                f.setText(" ");
            } catch (Exception e) {
                e.printStackTrace();

            }

        }

        else
        {
            Toast t=Toast.makeText(getApplicationContext(),"External storage not available", Toast.LENGTH_SHORT);
            t.show();
        }

        }

    public void addData(View view)//this function will be called when we click on INSERT button

    {   EditText n=(EditText)findViewById(R.id.editText21);
        EditText roll=(EditText)findViewById(R.id.editText22);
        String no=roll.getText().toString();

        String stud_name=n.getText().toString();

        db.insertData(no,stud_name);
        Toast t=Toast.makeText(getApplicationContext(),"Data inserted successfully!!", Toast.LENGTH_SHORT);
        t.show();
        n.setText(" ");
        roll.setText(" ");
        if(count>0)
        {
            count++;

            Cursor result=db.retrieveData();
            if(result.getCount()==0)
            {
                Toast t1=Toast.makeText(getApplicationContext(),"Table is empty", Toast.LENGTH_SHORT);
                t1.show();
            }
            else
            {

                String rollno="Roll No:\n";
                String name="Name:\n";
                int rows = result.getCount();
                result.moveToFirst();
                for(int ii=0;ii<rows;ii++)
                {
                    rollno=rollno.concat(result.getString(result.getColumnIndex("student_rollno")));
                    rollno=rollno.concat("\n");
                    name=name.concat(result.getString(result.getColumnIndex("student_name")));
                    name=name.concat("\n");
                    result.moveToNext();

                }
                t1.setText(rollno);
                t2.setText(name);


            }

        }
    }
    public void displayData(View view) {//this function will be called when we click on VIEW button
        count++;
        rd_file.setText(" ");

        Cursor result = db.retrieveData();
        if (result.getCount() == 0) {
            Toast t = Toast.makeText(getApplicationContext(), "Table is empty", Toast.LENGTH_SHORT);
            t.show();
        }
        else
        {
            String rollno="Roll No:\n";
            String name="Name:\n";
            int rows = result.getCount();
            result.moveToFirst();
            for(int ii=0;ii<rows;ii++)
            {
                rollno=rollno.concat(result.getString(result.getColumnIndex("student_rollno")));
                rollno=rollno.concat("\n");
                name=name.concat(result.getString(result.getColumnIndex("student_name")));
                name=name.concat("\n");
                result.moveToNext();

            }
            t1.setText(rollno);
            t2.setText(name);

        }
    }

    public void ondelbutClick(View view)//this function will be called when we click on DELETE button
    {
        System.out.print("hii in delete1");
        EditText r=(EditText)findViewById(R.id.editText22);
        System.out.print("hii in delete2");
        String rno=r.getText().toString();
        System.out.print("hii in delete3");
        int row_deleted=db.deleteRecord(rno);
        System.out.print("hii in delete4");
        System.out.print("hii in delete" +row_deleted);
        System.out.print("hii in delete5");
        if(row_deleted==0)
        {
            System.out.print("hii in delete6");
            Toast t=Toast.makeText(getApplicationContext(),"No such data found", Toast.LENGTH_SHORT);
            t.show();
        }
        else if(row_deleted>0)
        {
            Toast t=Toast.makeText(getApplicationContext(),"Data deleted successfully!!", Toast.LENGTH_SHORT);
            t.show();
        }

        if(count>0)
        {
            System.out.print("hii in delete7");
            count++;

            Cursor result=db.retrieveData();
            if(result.getCount()==0)
            {
                t1=(TextView)findViewById(R.id.textView5);
                t2=(TextView)findViewById(R.id.textView6);
                t1.setText(" ");
                t2.setText(" ");
                Toast t=Toast.makeText(getApplicationContext(),"Table is empty", Toast.LENGTH_SHORT);
                t.show();

            }
            else
            {

                    String rollno="Roll No:\n";
                    String name="Name:\n";
                    int rows = result.getCount();
                    result.moveToFirst();
                    for(int ii=0;ii<rows;ii++)
                    {
                        rollno=rollno.concat(result.getString(result.getColumnIndex("student_rollno")));
                        rollno=rollno.concat("\n");
                        name=name.concat(result.getString(result.getColumnIndex("student_name")));
                        name=name.concat("\n");
                        result.moveToNext();

                    }
                    t1.setText(rollno);
                    t2.setText(name);


            }

        }

    }
    public void onupdbutClick(View view) {//this function will be called when we click on UPDATE button
        EditText e2=(EditText)findViewById(R.id.editText22);
        String no = e2.getText().toString();
        EditText e1=(EditText)findViewById(R.id.editText21);

        String stud_name = e1.getText().toString();
       // String cur_sem = sem.getText().toString();
        int no_rows = db.updateRecord(no, stud_name);
        if (no_rows > 0)
        {
            Toast t=Toast.makeText(getApplicationContext(),"Data updated successfully!!", Toast.LENGTH_SHORT);
            t.show();
        }
        else if (no_rows ==0)
        {
            Toast t=Toast.makeText(getApplicationContext(),"NO such data found!!!", Toast.LENGTH_SHORT);
            t.show();
        }
        e2.setText(" ");
        e1.setText(" ");

        if(count>0)
        {
            count++;

            //System.out.println("Hi in view");
            Cursor result=db.retrieveData();
            if(result.getCount()==0)
            {
                Toast t=Toast.makeText(getApplicationContext(),"Table is empty", Toast.LENGTH_SHORT);
                t.show();
            }
            else {

                    String rollno="Roll No:\n";
                    String name="Name:\n";
                    int rows = result.getCount();
                    result.moveToFirst();
                    for(int ii=0;ii<rows;ii++)
                    {
                        rollno=rollno.concat(result.getString(result.getColumnIndex("student_rollno")));
                        rollno=rollno.concat("\n");
                        name=name.concat(result.getString(result.getColumnIndex("student_name")));
                        name=name.concat("\n");
                        result.moveToNext();

                    }
                    t1.setText(rollno);
                    t2.setText(name);

                }
            }
        }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {//function to save the activity state
        TextView tv1=(TextView) findViewById(R.id.textView);
        String r=tv1.getText().toString();
        TextView tv2=(TextView) findViewById(R.id.textView5);
        String rolldb=tv2.getText().toString();
        TextView tv3=(TextView) findViewById(R.id.textView6);
        String namedb=tv3.getText().toString();
        savedInstanceState.putString("save_rd_file",r);
        savedInstanceState.putString("save_t1",rolldb);
        savedInstanceState.putString("save_t2",namedb);
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {//function to restore the activity state
        //String r,rolldb,namedb;
        super.onRestoreInstanceState(savedInstanceState);
        rd_file.setText(savedInstanceState.getString("save_rd_file"));
        t1.setText(savedInstanceState.getString("save_t1"));
        t2.setText(savedInstanceState.getString("save_t2"));

    }


}








