package com.tang;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tang.AssetFragment;
import com.tang.activity.PropertyDetailActivity;
import com.tang.blockchain.Token;
import com.tang.blockchain.Wallet;
import com.tang.data_entity.TokenData;

import java.util.List;


//目前存在的问题是这个类中onCreateViewHolder不会被调用，导致没法显示。


public class AssetTokenInfoAdapter extends RecyclerView.Adapter<AssetTokenInfoAdapter.MyViewHolder>{

    private List<AssetFragment.AssetTokenItem> MTokenDatas ;
    private Context mContext;
   // private AddItemClickListener listener;  //监听回调


    public AssetTokenInfoAdapter(Context context, List<AssetFragment.AssetTokenItem> mTokenDatas) {
        super();
        MTokenDatas = mTokenDatas;
        mContext = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //这些函数的调用晚于创建该类时。
        Log.i("test", "onCreateViewHolder: test 1");
        int a=0;
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chain_list_item_asset, null, false));  //加载的是列表中单条
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {  //这个函数与上面的MyViewHolder针对每一条轮番调用，且MyViewHolder先调用。
        Log.i("test", "onCreateViewHolder: test 2");
//        Bitmap tokenSymbol=getItem(position).getSymbol();  图片暂时没有区别，都是eth图片
        String tokenName=getItem(position).getTokenInfo().name;

        //这个需要从链上实时获得。

        String tokenBlance=getItem(position).getBalance();
//        String tokenValue=getItem(position).getValue();
//        holder.TkenSymble.setImageBitmap(tokenSymbol);
        holder.TokenName.setText(tokenName);
        holder.TokenBalance.setText(tokenBlance);
//        holder.TokenValue.setText(tokenValue);

        setClickListener(holder,position);

//        holder.tvMusicName.setText(getItem(position).getMusicName());
//        //     holder.tvMusicName.setTextColor(myPurple);  //设置颜色
////        holder.ivState.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
//        Glide.with(mContext).load(getItem(position).getMusicAlbumPicPath()).error(R.drawable.back_add_playlist).into(holder.ivState);
//        //Glide.with(mContext).load(getItem(position).getMusicAlbumPicPath()).into(holder.ivState);
////        Glide.with(mContext).load(getItem(position).getMusicAlbumPicUrl()).into(holder.ivState);
//        if (getItem(position).isLove()){
//            holder.ivIsLove.setImageResource(R.drawable.ic_love);
//        }else {
//            holder.ivIsLove.setImageResource(R.drawable.ic_not_love);
//        }
//        setPlayAndIsLoveListener(holder, position);
//
//
//        if(position==mPlayingPosition){  //用于播放音乐的列表显示
//            holder.tvMusicName.setTextColor(myPurple);
//
//        }else{
//            holder.tvMusicName.setTextColor(Brack);
//
//        }

    }

    private void setClickListener(final MyViewHolder holder, final int position) {
        holder.TokenItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, PropertyDetailActivity.class);
                AssetFragment.AssetTokenItem token = MTokenDatas.get(position);

                intent.putExtra(C.EXTRA_BALANCE, token.balance);
                intent.putExtra(C.EXTRA_ADDRESS, Wallet.getInstance().getCurrentAddress());
                intent.putExtra(C.EXTRA_CONTRACT_ADDRESS, token.tokenInfo.address);
                intent.putExtra(C.EXTRA_SYMBOL, token.tokenInfo.symbol);
                intent.putExtra(C.EXTRA_DECIMALS, token.tokenInfo.decimals);//小数

                mContext.startActivity(intent);

            }
        });

    }





    /**
     * 获取其中的一个item
     * @param position position
     * @return ToeknData
     */
    public AssetFragment.AssetTokenItem getItem(int position){
        return MTokenDatas.get(position);
    }




    @Override
    public int getItemCount() {
        Log.i("test", "onCreateViewHolder: test 3");
        int a=MTokenDatas.size();
        return MTokenDatas.size();

    }


    //用于列表的显示
    private AssetFragment mfragment;
    public void SetFreagment(AssetFragment fragment){
        mfragment=fragment;

    }





    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView TkenSymble;
        TextView  TokenName;
        TextView TokenBalance;
        TextView TokenValue;  //换算成人民币或者美元的总价值，这个可以暂时不需要
        RelativeLayout TokenItem; //点击整个Item时触发点击事件
        MyViewHolder(View view) {
            super(view);
            TkenSymble = (ImageView) view.findViewById(R.id.token_symbol);
            TokenName  = (TextView)  view.findViewById(R.id.token_name);
            TokenBalance = (TextView) view.findViewById(R.id.token_balance);
            TokenValue = (TextView) view.findViewById(R.id.token_cny);
            TokenItem=(RelativeLayout)view.findViewById(R.id.token_item);

        }
    }
}
