package com.example.cheng.skindemo;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2017/6/18.
 */

public class SkinFactory implements LayoutInflaterFactory {

    private List<SkinView> cacheList = new ArrayList<>();

    private static final String[] prefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    /**
     *  系统 委托你来创建View，把自己修改生产好的View返回给系统显示
     */
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        06-18 05:39:45.232 7436-7436/com.example.cheng.skindemo E/seven:LinearLayout
//        06-18 05:39:45.232 7436-7436/com.example.cheng.skindemo E/seven:ViewStub
//        06-18 05:39:45.233 7436-7436/com.example.cheng.skindemo E/seven:FrameLayout
//        06-18 05:39:45.234 7436-7436/com.example.cheng.skindemo E/seven: android.support.constraint.ConstraintLayout
//        06-18 05:39:45.257 7436-7436/com.example.cheng.skindemo E/seven: TextView
        //生产View   区别对待 自定义控件  和系统控件
        View view = null;
        if (name.contains(".")) {
            //自定义控件 v4  v7
            view = createView(context, attrs, name);
        } else {
            //系统控件
            for (String pre : prefixList) {
                view = createView(context, attrs, pre + name);
                if (view != null) {
                    break;
                }
            }
        }

        //收集需要换肤的控件
        if (view != null) {
            parseSkinView(context, attrs, view);
        }

        return view;
    }

    //收集需要换肤的View
    private void parseSkinView(Context context, AttributeSet attrs, View view) {
        List<SkinItem> list = new ArrayList<>();

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            //background
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            //拥有这两个属性
            if (attrName.equals("background") || attrName.equals("textColor")) {
                //Value是以@开头的如 @0xffffff
                int id = Integer.parseInt(attrValue.substring(1));
                //color
                String entry_name = context.getResources().getResourceEntryName(id);
                String typeName = context.getResources().getResourceTypeName(id);
                //封装JavaBean   android:background="@color/colorPrimaryDark"
                SkinItem skinItem = new SkinItem(attrName, id, entry_name, typeName);
                list.add(skinItem);
            }
        }

        if (!list.isEmpty()) {
            SkinView skinView = new SkinView(view, list);
            cacheList.add(skinView);
            //应用换肤
            skinView.apply();
        }

    }

    /**
     * 手动换肤
     */
    public void apply() {
        for (SkinView skinView : cacheList) {
            skinView.apply();
        }
    }

    class SkinView {
        private View view;
        private List<SkinItem> list;

        public SkinView(View view, List<SkinItem> list) {
            this.view = view;
            this.list = list;
        }

        /**
         * 换肤的代码
         */
        public void apply() {
            for (SkinItem skinItem : list) {
                if ("textColor".equals(skinItem.getAttrName())) {
                    if (view instanceof TextView) {
                        ((TextView) view).setTextColor(SkinManager.getInstance().getColor(skinItem.getRefId()));
                    }
                } else if ("background".equals(skinItem.getAttrName())) {
                    //区别对待  背景  颜色 图片之分
                    if ("color".equals(skinItem.getAttrType())) {
                        //赋值
                        view.setBackgroundColor(SkinManager.getInstance().getColor(skinItem.getRefId()));
                    } else if ("drawable".equals(skinItem.getAttrType())) {
                        view.setBackground(SkinManager.getInstance().getDrawable(skinItem.getRefId()));
                    }
                }
            }
        }
    }

    class SkinItem {
        /**
         * android:background="@color/colorAccent"
         * <p>
         * attrName = background
         */
        String attrName;
        //在R文件表示的ID值
        int refId;
        //资源名称 @color/colorAccent
        String attrValue;
        //drawable  color
        String attrType;

        public SkinItem(String attrName, int refId, String attrValue, String attrType) {
            this.attrName = attrName;
            this.refId = refId;
            this.attrValue = attrValue;
            this.attrType = attrType;
        }

        public String getAttrName() {
            return attrName;
        }

        public void setAttrName(String attrName) {
            this.attrName = attrName;
        }

        public int getRefId() {
            return refId;
        }

        public void setRefId(int refId) {
            this.refId = refId;
        }

        public String getAttrValue() {
            return attrValue;
        }

        public void setAttrValue(String attrValue) {
            this.attrValue = attrValue;
        }

        public String getAttrType() {
            return attrType;
        }

        public void setAttrType(String attrType) {
            this.attrType = attrType;
        }
    }

    private View createView(Context context, AttributeSet attrs, String name) {
        try {
            Class viewClazz = context.getClassLoader().loadClass(name);
            Constructor<? extends View> constructor = viewClazz.getConstructor(new Class[]{Context.class, AttributeSet.class});
            return constructor.newInstance(context, attrs);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
