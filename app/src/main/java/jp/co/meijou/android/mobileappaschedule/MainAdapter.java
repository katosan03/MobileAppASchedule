package jp.co.meijou.android.mobileappaschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<MainActivity2.RowData> rowDataList;

    MainAdapter(List<MainActivity2.RowData> rowDataList) {
        this.rowDataList = rowDataList;
    }

    /**
     * 一行分のデータ
     */
    static class MainViewHolder extends RecyclerView.ViewHolder {
        TextView jikanTitle;
        TextView naiyouContents;

        MainViewHolder(@NonNull View itemView) {
            super(itemView);
            jikanTitle = itemView.findViewById(R.id.jikan_text_view);
            naiyouContents = itemView.findViewById(R.id.naiyou_text_view);
        }
    }

    /**
     * ViewHolder作るメソッド
     * 最初しか呼ばれない。
     * ここでViewHolderのlayoutファイルをインフレーとして生成したViewHolderをRecyclerViewに返す。
     */
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view, parent, false);
        return new MainViewHolder(view);
    }

    /**
     * ViewHolderとRecyclerViewをバインドする
     * 一行のViewに対して共通でやりたい処理をここで書く。今回はテキストのセットしかしてないけど。
     */
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        MainActivity2.RowData rowData = this.rowDataList.get(position);
        holder.jikanTitle.setText(rowData.jikanTitle);
        holder.naiyouContents.setText(rowData.naiyouContents);
    }

    /**
     * リストの行数
     */
    @Override
    public int getItemCount() {
        return rowDataList.size();
    }
}
