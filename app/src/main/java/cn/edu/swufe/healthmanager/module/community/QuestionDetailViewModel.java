package cn.edu.swufe.healthmanager.module.community;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Comment;

import java.lang.reflect.Type;
import java.util.List;

import cn.edu.swufe.healthmanager.model.LoginUser;
import cn.edu.swufe.healthmanager.model.ServerResult;
import cn.edu.swufe.healthmanager.model.entities.CommentEntity;
import cn.edu.swufe.healthmanager.model.entities.UserEntity;
import cn.edu.swufe.healthmanager.util.OkHttpUtils;
import cn.edu.swufe.healthmanager.util.UrlUtil;


public class QuestionDetailViewModel extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();

    MutableLiveData<ServerResult> commentRlt = new MutableLiveData<>();
    MutableLiveData<ServerResult<List<CommentEntity>>> commentListRlt = new MutableLiveData<>();

    public MutableLiveData<ServerResult> getCommentRlt() {
        return commentRlt;
    }

    public MutableLiveData<ServerResult<List<CommentEntity>>> getCommentListRlt() {
        return commentListRlt;
    }

    /**
     * 提交一条评论
     * @param commentStr
     * @param questionId
     */
    public void sendComment(String commentStr, String questionId){
        UserEntity loginUser;
        String url, requestJsonStr;
        Type rltType;
        CommentEntity commentEntity = new CommentEntity();

        // 1. 获得用户信息
        loginUser = LoginUser.getInstance().getUserEntity();

        // 2. 生成URL
        url = UrlUtil.uploadCommentURL(loginUser.getTokenKey());
        Log.i(TAG, "requestUrl: " + url);

        // 3. 生成JSON信息
        commentEntity.setUserInfo(loginUser);
        commentEntity.setUccId(questionId);
        commentEntity.setContent(commentStr);

        requestJsonStr = new Gson().toJson(commentEntity);
        Log.i(TAG, "requestJsonStr: " + requestJsonStr);

        // 4. 发出http请求, 处理请求结果
        rltType = new TypeToken<ServerResult>(){}.getType();
        OkHttpUtils.post_test(url, requestJsonStr, commentRlt, rltType);
    }

    /**
     * 查询评论列表
     * @param page
     * @param size
     * @param questionId
     */
    public void getComments(int page, int size, String questionId){
        // todo: 查询问题的所有评论
        String url;
        Type rltType;

        // 1. 生成请求url
        url = UrlUtil.getCommentsListURL(page, size, questionId);

        // 2. 请求服务器数据，并处理请求结果
        rltType = new TypeToken<ServerResult<List<CommentEntity>>>(){}.getType();
        OkHttpUtils.get_test(url, commentListRlt, rltType);

    }

}
