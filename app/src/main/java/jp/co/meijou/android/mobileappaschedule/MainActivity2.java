package jp.co.meijou.android.mobileappaschedule;

//特定の日の内容を表示するページ

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import jp.co.meijou.android.mobileappaschedule.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private PrefDataStore prefDataStore;
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private List<MainAdapter> dataList;
    private String day;
    private int kosuu = 0;


    String times[];
    String plans[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefDataStore = prefDataStore.getInstance(this);
        prefDataStore.getString("day").ifPresent(datas -> day = datas.toString());
        binding.textday.setText(day.substring(0,4) + "/" + day.substring(4, 6) + "/"+ day.substring(6, 8));




        //プラスボタンを押した場合（予定を追加する）
        binding.buttonPlus.setOnClickListener(view ->{
            var intent = new Intent(this, MainActivity3.class);
            startActivity(intent);
        });

        //戻るボタンを押した場合（MainActivityに戻る）
        binding.buttonReturn.setOnClickListener(view ->{
            var intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        // RecyclerViewのレイアウトサイズを変更しない設定をONにする
        // パフォーマンス向上のための設定。
        recyclerView.setHasFixedSize(true);

        // RecyclerViewにlayoutManagerをセットする。
        // このlayoutManagerの種類によって「1列のリスト」なのか「２列のリスト」とかが選べる。
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Adapter生成してRecyclerViewにセット
        RecyclerView.Adapter mainAdapter = new MainAdapter(createRowData());
        recyclerView.setAdapter(mainAdapter);
    }

    private List<RowData> createRowData() {
        List<RowData> dataSet = new ArrayList<>();
        prefDataStore.getString(day).ifPresent(kosu -> kosuu = Integer.parseInt(kosu));
        int i = 0;
        while (i <= kosuu) {
            RowData data = new RowData();

            String name = day + kosuu;
            String[] arr = new String[2];
            prefDataStore.getString(name).ifPresent(str -> arr[0] = str);
            if(arr[0] != null){
                String[] naiyou = arr[0].split("-");
                String jikoku = naiyou[0];
                data.jikanTitle = jikoku.substring(0, 2) + "："+ jikoku.substring(2, 4);;;
                data.naiyouContents = arr[1];
            }

            dataSet.add(data);
            i = i + 1;
        }
        return dataSet;
    }

    class RowData {
        String jikanTitle;
        String naiyouContents;
    }

}

/*

https://qiita.com/toya108/items/7f92c8088d84d1f60434
 */