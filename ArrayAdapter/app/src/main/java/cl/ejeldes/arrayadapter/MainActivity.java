package cl.ejeldes.arrayadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listItems = findViewById(R.id.list_items);

        Person person1 = new Person("Emilio", "Jeldes");
        Person person2 = new Person("Carlos", "Jeldes");
        Person person3 = new Person("Pedro", "Campos");
        Person person4 = new Person("Maximiliano", "Alarcon");

        Person[] items = new Person[]{person1, person2, person3, person4};

        ArrayAdapter<Person> adapterList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        listItems.setAdapter(adapterList);
    }
}
