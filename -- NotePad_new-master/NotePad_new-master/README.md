

一、功能扩展如下：

1.增加时间戳显示

2.添加笔记查询功能

附加功能：

3.笔记排序

4.UI美化

二、主界面增加时间戳：

效果如下：(如果图片加载较慢，建议慢慢等一下就会加载出来)

![098d9e6b3b2647edaa60618d86b26d6](https://github.com/user-attachments/assets/ae9d0374-70b9-4d15-b3b9-ae6aa4e22233)





修改Note1后时间戳更新后效果：

![57d23a3d2094d3e417427c248ff33ee](https://github.com/user-attachments/assets/2ab83619-d7fb-4b4d-9015-f9ced4d14839)





1.在NoteList类的PROJECTION方法中定义时间戳的显示功能如下:

private static final String[] PROJECTION = new String[] {
NotePad.Notes._ID, // 0
NotePad.Notes.COLUMN_NAME_TITLE, // 1
NotePad.Notes.COLUMN_NAME_CREATE_DATE,//2
NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE//3
};

2.新建DateUtil.java并编写一个StringToDate方法定义字符串转换日期格式：

public static String StringToDate(String str_data)
{
String beginDate=str_data;
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String sd = sdf.format(new Date(Long.parseLong(beginDate)));
return  sd;
}

3.在NoteList类的dataColumns数组和viewIDs数组中加入时间戳的数据和view的Id如下:

private String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE ,
NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE } ;
private int[] viewIDs = { android.R.id.text1 , R.id.time };

4.在NotePadeProvider.java 的insert方法中加入对日期的格式转换如下:

//获取当前系统时间
Long nowtime = Long.valueOf(System.currentTimeMillis());
Date date = new Date(nowtime);
SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
String Time = format.format(date);

// 如果值映射中不包含创建日期，则将值设置为当前时间。
if (values.containsKey(NotePad.Notes.COLUMN_NAME_CREATE_DATE) == false) {
values.put(NotePad.Notes.COLUMN_NAME_CREATE_DATE, Time);
}

// 如果值映射中不包含修改日期，则将值设置为当前时间。
if (values.containsKey(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE) == false) {
values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, Time);
}

5.在NodeEditor的updateNote方法中转换日期格式如下:

SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式     values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, df.format(new Date()));

6.在noteslistitem.xml文件添加list布局中时间戳的textview如下：   <TextViewandroid:layout_width="match_parent"
android:layout_height="wrap_content"
android:text="2024/11/28 22:22:22"
android:layout_marginTop="5dp"
android:layout_marginLeft="10dp"
android:id="@+id/tv_data"
android:textColor="#000000"
/>

三、笔记搜索

效果：

![57d23a3d2094d3e417427c248ff33ee](https://github.com/user-attachments/assets/d188c6bb-266b-4780-aec9-f69d1cc369e5)

搜索存在的笔记标题效果：

![b192a648f952b70789f100c70b1c44e](https://github.com/user-attachments/assets/fea5dd4b-b961-4afc-bc2b-d5d39052f69a)


搜索不存在的笔记标题效果：

![cdfd6c1d508f4e71a1d8ebb7a5ea095](https://github.com/user-attachments/assets/00255a09-5deb-49ff-812f-6bd3a5db96d5)


1.在noteslist_layout.xml中添加搜索功能的图标定义如下:  <ImageView android:id="@+id/iv_searchnotes"
android:layout_width="30dp"   。
android:layout_height="29dp"
android:layout_marginLeft="10dp"
android:layout_marginTop="5dp"
android:layout_marginRight="10dp"
android:background="@drawable/search1" />

2.在NoteLIst类的onOptionsItemSelected方法中添加可能的几种情况搜索情况:

case R.id.menu_search:
Intent intent = new Intent();
intent.setClass(NotesList.this,NoteSearch.class);
NotesList.this.startActivity(intent);
return true;

<FrameLayout, android:layout_width="match_parent" 
android:layout_weight="1"
android:orientation="vertical"
android:layout_height="wrap_content">
<ListView
android:layout_width="match_parent"
android:layout_height="match_parent"
android:cacheColorHint="#00000000"
android:divider="#FFFFCC"
android:dividerHeight="0dp"
android:id="@id/android:list"

3.在布局文件noteslist_layout.xml中新建搜素框和列表的view布局

<LinearLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:orientation="horizontal"

        >
        <EditText
            android:layout_width="match_parent"
            android:layout_height="40dp"
            android:layout_weight="1"
            android:layout_marginLeft="10dp"
            android:textColor="#000000"
            android:id="@+id/et_Search"
            android:hint="输入标题查找"
            android:textCursorDrawable="@drawable/contact_edit_edittext_normal"
            />
    
        <ImageView
            android:id="@+id/iv_searchnotes"
            android:layout_width="30dp"
            android:layout_height="29dp"
            android:layout_marginLeft="10dp"
            android:layout_marginTop="5dp"
            android:layout_marginRight="10dp"
            android:background="@drawable/search1" />
    
    </LinearLayout>
    
    <FrameLayout
    android:layout_width="match_parent"
    android:layout_weight="1"
    android:orientation="vertical"
    android:layout_height="wrap_content">
    <ListView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:cacheColorHint="#00000000"
        android:divider="#FFFFCC"
        android:dividerHeight="0dp"
        android:id="@id/android:list"
    
        >
    
    </ListView>
        <ImageButton
            android:id="@+id/fab"
            android:layout_width="60dp"
            android:layout_height="60dp"
            android:layout_gravity="bottom|center"
            android:layout_margin="45dp"
            android:background="@drawable/tianjia"
            android:backgroundTintMode="add"/>


</FrameLayout>


        <!--fab:fab_colorNormal="@color/primary"-->
        <!--fab:fab_colorPressed="@color/primary_pressed"-->
        <!--fab:fab_colorRipple="@color/ripple" />-->
    <!--<ImageView-->
        <!--android:layout_marginTop="20dp"-->
        <!--android:layout_width="80dp"-->
        <!--android:layout_height="80dp"-->
        <!--android:background="@drawable/tianjia"-->
        <!--android:layout_gravity="center_horizontal"-->
        <!--android:layout_marginBottom="20dp"-->
        <!--android:id="@+id/iv_addnotes"-->
        <!--/>-->

</LinearLayout>

4.使用TextWatcher实现textview输入监听，使用数据库语句like实现模糊查找：

       private void addSearchView() {
           //给listview添加头部(search)
           View v=View.inflate(this, R.layout.notelistheader,null);
           getListView().addHeaderView(v);
           //给搜索框添加搜索功能
           final EditText et_Search=(EditText)v.findViewById(R.id.et_search);
           et_Search.addTextChangedListener(new TextWatcherForSearch(){
           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   super.onTextChanged(charSequence, i, i1, i2);
                   if (charSequence.length()!=0 && et_Search.getText().toString().length()!=0){
                   String str_Search = et_Search.getText().toString();
                   Cursor search_cursor = managedQuery(
                               getIntent().getData(),            
                               PROJECTION,                       
                               NotePad.Notes.COLUMN_NAME_TITLE+" like ?",   
                               new String[]{"%"+str_Search+"%"}, //匹配字符串条件                           
                       NotePad.Notes.DEFAULT_SORT_ORDER  // Use the default sort order.
               );
               adapter.swapCursor(search_cursor);//刷新listview
    
           }else {
               if (cursor!=null)//删除搜索框中的text后刷新listview
               adapter.swapCursor(cursor);//刷新listview
           }
       }

5.在mainifests.xml文件中注册Search功能:
<activity
<
category .   android:name="android.intent.category.SELECTED_ALTERNATIVE" 
/>
</
activity>

四、笔记排序

效果展示：

![ad3fd0a73192fbb5df38f50b352faad](https://github.com/user-attachments/assets/1b0e5a0d-c905-4fad-a115-69307ab814ff)


按创建时间排序:

![95fbbde873f5df68f32d74fccf6c200](https://github.com/user-attachments/assets/7588d870-0677-482d-910b-ef1f772c292f)


按修改时间排序:

![26c163fc8d5584ad7a6b9f0211f8950](https://github.com/user-attachments/assets/729dce4c-e991-4415-be99-523f742d32a9)



1.在菜单文件list_options_menu.xml中添加菜单选项: <item .    android:id="@+id/menu_sort1"
android:title="按创建时间排序"/>
<item    .   android:id="@+id/menu_sort2"
android:title="按修改时间排序"/>

2.在NoteList.java中添加三种可能的排序方式的情况：

//按照创建时间排序
case R.id.menu_sort1:
cursor = managedQuery(
getIntent().getData(),            
PROJECTION,                      
null,                          
null,                          
NotePad.Notes._ID
);
adapter = new MyCursorAdapter(
this,
R.layout.noteslist_item,
cursor,
dataColumns,
viewIDs
);
setListAdapter(adapter);
return true;
//按照修改时间排序
case R.id.menu_sort2:
cursor = managedQuery(
getIntent().getData(),          
PROJECTION,                      
null,                            
null,                       
NotePad.Notes.DEFAULT_SORT_ORDER
);
adapter = new MyCursorAdapter(
this,
R.layout.noteslist_item,
cursor,
dataColumns,
viewIDs
);
setListAdapter(adapter);
return true;
//按照颜色排序
case R.id.menu_sort3:
cursor = managedQuery(
getIntent().getData(),
PROJECTION,      
null,       
null,       
NotePad.Notes.COLUMN_NAME_BACK_COLOR
);
adapter = new MyCursorAdapter(
this,
R.layout.noteslist_item,
cursor,
dataColumns,
viewIDs
);
setListAdapter(adapter);
return true;





五、UI美化：更换主题界面

效果：

![img_7](https://github.com/user-attachments/assets/275ec380-64a9-419f-ae19-8eda38009adf)

![img_8](https://github.com/user-attachments/assets/5eabcf65-2e73-4afd-a437-672bb0b82daa)

![img_9](https://github.com/user-attachments/assets/26e74081-bc9e-4002-9b66-0b4d6c730fc4)

![img_10](https://github.com/user-attachments/assets/5759d798-0a5b-440f-a768-6f64f6d01b8b)

![img_11](https://github.com/user-attachments/assets/33c5f117-cb53-46fd-b919-323575d62184)

![img_12](https://github.com/user-attachments/assets/ebae136e-00a1-42ed-8bb9-f703f896087a)

![img_13](https://github.com/user-attachments/assets/7b7e1eac-1eda-4652-a2af-e864727fbfcd)


1.在list_options_menu.xml中添加主题背景的菜单选项：<item   .   android:id="@+id/bg_change"
   android:title="@string/background"
   />

2.在Notelist类的ColorSelect方法中添加更换各个主题背景case：

public void ColorSelect(View view){

String color;

switch(view.getId()){

case R.id.pink:
Drawable btnDrawable1 = getResources().getDrawable(R.drawable.pink);
ll_noteList.setBackgroundDrawable(btnDrawable1);
lv_notesList.setBackgroundDrawable(btnDrawable1)
;
break;

case R.id.Yello:
Drawable btnDrawable2 = getResources().getDrawable(R.drawable.yellow);
ll_noteList.setBackgroundDrawable(btnDrawable2);
lv_notesList.setBackgroundDrawable(btnDrawable2);

break;

case R.id.PaleVioletRed:
Drawable btnDrawable3 = getResources().getDrawable(R.drawable.palevioletred);
ll_noteList.setBackgroundDrawable(btnDrawable3);
lv_notesList.setBackgroundDrawable(btnDrawable3);

break;

case R.id.LightGrey:
Drawable btnDrawable4 = getResources().getDrawable(R.drawable.lightgrey);
ll_noteList.setBackgroundDrawable(btnDrawable4);
lv_notesList.setBackgroundDrawable(btnDrawable4);

break;

case R.id.MediumPurple:
Drawable btnDrawable5 = getResources().getDrawable(R.drawable.mediumpurple);
ll_noteList.setBackgroundDrawable(btnDrawable5);
lv_notesList.setBackgroundDrawable(btnDrawable5);

break;

case R.id.DarkGray:
Drawable btnDrawable6 = getResources().getDrawable(R.drawable.darkgray);
ll_noteList.setBackgroundDrawable(btnDrawable6);
lv_notesList.setBackgroundDrawable(btnDrawable6);

break;

case R.id.Snow:
Drawable btnDrawable7 = getResources().getDrawable(R.drawable.snow);
ll_noteList.setBackgroundDrawable(btnDrawable7);
lv_notesList.setBackgroundDrawable(btnDrawable7);

break;

}

}

