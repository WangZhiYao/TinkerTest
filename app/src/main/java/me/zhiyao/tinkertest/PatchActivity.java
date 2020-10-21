package me.zhiyao.tinkertest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author WangZhiYao
 * @date 2020/10/21
 */
public class PatchActivity extends AppCompatActivity {

    @BindView(R.id.wv_patch)
    WebView mWvPatch;

    public static void start(Context context, String repoUrl) {
        Intent starter = new Intent(context, PatchActivity.class);
        starter.putExtra(ExtraKey.REPO_URL, repoUrl);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patch);
        ButterKnife.bind(this);

        Intent data = getIntent();
        String url = data.getStringExtra(ExtraKey.REPO_URL);
        if (url != null && !url.isEmpty()) {
            mWvPatch.loadUrl(url);
        }
    }
}
