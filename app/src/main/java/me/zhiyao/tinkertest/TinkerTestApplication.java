package me.zhiyao.tinkertest;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @author WangZhiYao
 * @date 2020/10/16
 */
public class TinkerTestApplication extends TinkerApplication {

    public TinkerTestApplication() {
        super(
                ShareConstants.TINKER_ENABLE_ALL,
                "me.zhiyao.tinkertest.TinkerTestApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader",
                false,
                true
        );
    }
}
