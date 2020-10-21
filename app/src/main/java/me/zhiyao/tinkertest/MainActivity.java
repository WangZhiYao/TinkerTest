package me.zhiyao.tinkertest;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.meituan.android.walle.WalleChannelReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author WangZhiYao
 * @date 2020/10/16
 */
public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_CHANNEL = "DefaultChannel";

    private static final String REPO_URL = "https://github.com/WangZhiYao/TinkerTest";

    @BindView(R.id.tv_main_channel)
    TextView mTvMainChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String channel = WalleChannelReader.getChannel(this, DEFAULT_CHANNEL);
        mTvMainChannel.setText(channel);
    }

    @OnClick(R.id.btn_main_patch)
    public void onStartPatchActivityClicked() {
        PatchActivity.start(this, REPO_URL);
    }
}