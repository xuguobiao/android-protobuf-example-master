package com.example.android;

import com.google.protobuf.InvalidProtocolBufferException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.protobuf.R;
import com.example.tutorial.AddressBookProtos;
import com.example.tutorial.AddressBookProtos.AddressBook;
import com.example.tutorial.AddressBookProtos.Person;
import com.googlecode.protobuf.format.JsonFormat;
import com.googlecode.protobuf.format.XmlFormat;

import java.util.Arrays;
import java.util.logging.XMLFormatter;

public class TestActivity extends Activity implements View.OnClickListener {


    Button readButton, writeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        readButton = (Button) findViewById(R.id.read_button);
        writeButton = (Button) findViewById(R.id.write_button);

        readButton.setOnClickListener(this);
        writeButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == readButton) {

        } else if (view == writeButton) {
            Person.Builder personBuilder = Person.newBuilder();
            personBuilder.setEmail("everlastxgb@gmail.com");
            personBuilder.setId(2009);
            personBuilder.setName("xuguobiao");

            Person.PhoneNumber.Builder phoneBuilder = Person.PhoneNumber.newBuilder();
            phoneBuilder.setNumber("13800138000");
            phoneBuilder.setType(Person.PhoneType.MOBILE);

            personBuilder.addPhone(phoneBuilder.build());

            Person person = personBuilder.build();


//            Person personProto = Person.getDefaultInstance();
            XmlFormat xmlFormat = new XmlFormat();
            String xmlString = xmlFormat.printToString(person);
            String jsonString = new JsonFormat().printToString(person);
            System.out.println("Kido==> " + "xmlString->" + xmlString);
            System.out.println("Kido==> " + "jsonString->" + jsonString);

            Person.Builder pb = Person.newBuilder();
            try {
                new JsonFormat().merge(jsonString, pb);
            } catch (JsonFormat.ParseException e) {
                e.printStackTrace();
            }
            Person pp = pb.build();
            System.out.println("Kido==> " + "pp->" + pp);


            System.out.println("Kido==> " + "打印输出Person对象信息：");
            System.out.println("Kido==> " + person);
            System.out.println("Kido==> " + "Person对象调用toString()方法：");
            System.out.println("Kido==> " + person.toString());

            System.out.println("Kido==> " + "Person对象字段是否初始化：" + person.isInitialized());

            // 序列号
            System.out.println("Kido==> " + "Person对象调用toByteString()方法：");
            System.out.println("Kido==> " + person.toByteString());

            System.out.println("Kido==> " + "Person对象调用toByteArray()方法:");
            System.out.println("Kido==> " + Arrays.toString(person.toByteArray()));

            try {
                System.out.println("Kido==> " + "反序列化后的对象信息：");
                // 反序列化
                Person newPerson = Person.parseFrom(person.toByteArray());
                System.out.println("Kido==> " + newPerson);
                newPerson = Person.parseFrom(person.toByteString());
                System.out.println("Kido==> " + newPerson);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }

            // 向地址簿添加两条Person信息
            AddressBook.Builder books = AddressBook.newBuilder();
            books.addPerson(person);
            books.addPerson(Person.newBuilder(person).setEmail("tom@163.com")
                    .build());
            System.out.println("Kido==> " + "AddressBook对象信息：");
            System.out.println("Kido==> " + books.build());

        }
    }

}