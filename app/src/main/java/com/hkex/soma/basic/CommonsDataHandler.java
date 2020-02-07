package com.hkex.soma.basic;

import android.content.Context;
import com.hkex.soma.JSONParser.CommonListJSONParser;
import com.hkex.soma.JSONParser.GenericJSONParser;
import com.hkex.soma.JSONParser.UnderlyingListJSONParser;
import com.hkex.soma.R;
import com.hkex.soma.dataModel.CommonList;
import com.hkex.soma.dataModel.FuturesList_Result;
import com.hkex.soma.dataModel.FuturesPList_Result;
import com.hkex.soma.dataModel.IndexList;
import com.hkex.soma.dataModel.UnderlyingList;
import com.hkex.soma.utils.Commons;
import com.hkex.soma.utils.CommonsIndex;

public class CommonsDataHandler {
    private Context context;
    private boolean data1done = false;
    private boolean data1fail = false;
    private boolean data2done = false;
    private boolean data2fail = false;
    private boolean data3done = false;
    private boolean data3fail = false;
    private boolean data4done = false;
    private boolean data4fail = false;
    private boolean data_index_done = false;
    private boolean data_underlying_done = false;
    private boolean indexoptionsList_done = false;
    private OnDataCompletedListener onDataCompletedListener = null;
    private OnFailedToLoadListener onFailedToLoadListener = null;
    private boolean optionsList_done = false;

    public interface OnDataCompletedListener {
        void onDataCompleted();
    }

    public interface OnFailedToLoadListener {
        void onFailed(Runnable runnable, Exception exc);
    }

    public CommonsDataHandler(Context context2) {
        this.context = context2;
    }

    private void load_plist(String str) {
        GenericJSONParser genericJSONParser = new GenericJSONParser(FuturesPList_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<FuturesPList_Result>() {
            public void OnJSONCompleted(FuturesPList_Result futuresPList_Result) {
                Commons.LogDebug("", futuresPList_Result.toString());
                CommonsDataHandler.this.plistdataResult(futuresPList_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                boolean unused = CommonsDataHandler.this.data4done = false;
                boolean unused2 = CommonsDataHandler.this.data4fail = true;
                if (CommonsDataHandler.this.onFailedToLoadListener != null) {
                    CommonsDataHandler.this.onFailedToLoadListener.onFailed(runnable, exc);
                }
            }
        });
        genericJSONParser.loadXML(this.context.getString(R.string.url_futures) + "&type=PList&id=" + str);
    }

    public void commonsIndexListJSONResult(IndexList indexList) {
        Commons.indexList = indexList;
        this.data_index_done = true;
        if (this.data_underlying_done && this.data_index_done) {
            this.data2done = true;
        }
        if (!this.data1fail && !this.data2fail && !this.data3fail && this.data2done) {
            Commons.CommonsListRequireUpdate = false;
        }
        if (this.data1done && this.data2done && this.data3done && this.data4done && this.onDataCompletedListener != null) {
            this.onDataCompletedListener.onDataCompleted();
        }
    }

    public void commonsListJSONResult(UnderlyingList underlyingList) {
        Commons.underlyingList = underlyingList;
        this.data_underlying_done = true;
        if (this.data_underlying_done && this.data_index_done) {
            this.data2done = true;
        }
        if (!this.data1fail && !this.data2fail && !this.data3fail && this.data2done) {
            Commons.CommonsListRequireUpdate = false;
        }
        if (this.data1done && this.data2done && this.data3done && this.data4done && this.onDataCompletedListener != null) {
            this.onDataCompletedListener.onDataCompleted();
        }
    }

    public void indexoptionsListJSONResult(CommonList commonList) {
        CommonsIndex.commonList = commonList;
        if (commonList != null) {
            CommonsIndex.defaultStockCode = commonList.getIndexcode();
            CommonsIndex.defaultStockName = Commons.language.equals("en_US") ? commonList.getIndexname() : commonList.getIndexnmll();
            CommonsIndex.defaultUnderlyingCode = commonList.getIndexcode();
        }
        this.indexoptionsList_done = true;
        if (this.indexoptionsList_done && this.optionsList_done) {
            this.data1done = true;
        }
        if (!this.data1fail && !this.data2fail && !this.data3fail && !this.data4fail) {
            CommonsIndex.CommonsListRequireUpdate = false;
        }
        if (this.data1done && this.data2done && this.data3done && this.data4done && this.onDataCompletedListener != null) {
            this.onDataCompletedListener.onDataCompleted();
        }
    }

    public void loadCommonsListJSON() {
        this.data2done = false;
        UnderlyingListJSONParser underlyingListJSONParser = new UnderlyingListJSONParser();
        underlyingListJSONParser.setOnJSONCompletedListener(new UnderlyingListJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(UnderlyingList underlyingList) {
                Commons.LogDebug("", underlyingList.toString());
                CommonsDataHandler.this.commonsListJSONResult(underlyingList);
            }
        });
        underlyingListJSONParser.setOnJSONFailedListener(new UnderlyingListJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                Commons.CommonsListRequireUpdate = true;
                boolean unused = CommonsDataHandler.this.data2fail = true;
                if (CommonsDataHandler.this.onFailedToLoadListener != null) {
                    CommonsDataHandler.this.onFailedToLoadListener.onFailed(runnable, exc);
                }
                CommonsDataHandler.this.commonsListJSONResult((UnderlyingList) null);
            }
        });
        underlyingListJSONParser.loadXML(this.context.getString(R.string.url_common_list) + "?type=underlyingList");
        GenericJSONParser genericJSONParser = new GenericJSONParser(IndexList.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<IndexList>() {
            public void OnJSONCompleted(IndexList indexList) {
                Commons.LogDebug("", indexList.toString());
                CommonsDataHandler.this.commonsIndexListJSONResult(indexList);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                Commons.CommonsListRequireUpdate = true;
                boolean unused = CommonsDataHandler.this.data2fail = true;
                if (CommonsDataHandler.this.onFailedToLoadListener != null) {
                    CommonsDataHandler.this.onFailedToLoadListener.onFailed(runnable, exc);
                }
                CommonsDataHandler.this.commonsIndexListJSONResult((IndexList) null);
            }
        });
        genericJSONParser.loadXML(this.context.getString(R.string.url_common_list) + "?type=indexList");
    }

    public void loadOptionsListJSON() {
        this.data1done = false;
        CommonListJSONParser commonListJSONParser = new CommonListJSONParser();
        commonListJSONParser.setOnJSONCompletedListener(new CommonListJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(CommonList commonList) {
                Commons.LogDebug("", commonList.toString());
                CommonsDataHandler.this.optionsListJSONResult(commonList);
            }
        });
        commonListJSONParser.setOnJSONFailedListener(new CommonListJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                Commons.CommonsListRequireUpdate = true;
                boolean unused = CommonsDataHandler.this.data1fail = true;
                if (CommonsDataHandler.this.onFailedToLoadListener != null) {
                    CommonsDataHandler.this.onFailedToLoadListener.onFailed(runnable, exc);
                }
                CommonsDataHandler.this.optionsListJSONResult((CommonList) null);
            }
        });
        commonListJSONParser.loadXML(this.context.getString(R.string.url_class_list));
        try {
            CommonListJSONParser commonListJSONParser2 = new CommonListJSONParser();
            commonListJSONParser2.setOnJSONCompletedListener(new CommonListJSONParser.OnJSONCompletedListener() {
                public void OnJSONCompleted(CommonList commonList) {
                    Commons.LogDebug("", commonList.toString());
                    CommonsDataHandler.this.indexoptionsListJSONResult(commonList);
                }
            });
            commonListJSONParser2.setOnJSONFailedListener(new CommonListJSONParser.OnJSONFailedListener() {
                public void OnJSONFailed(Runnable runnable, Exception exc) {
                    exc.printStackTrace();
                    Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                    CommonsIndex.CommonsListRequireUpdate = true;
                    boolean unused = CommonsDataHandler.this.data1fail = true;
                    if (CommonsDataHandler.this.onFailedToLoadListener != null) {
                        CommonsDataHandler.this.onFailedToLoadListener.onFailed(runnable, exc);
                    }
                    CommonsDataHandler.this.indexoptionsListJSONResult((CommonList) null);
                }
            });
            commonListJSONParser2.loadXML(this.context.getString(R.string.url_index_class_list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadVersionJSON() {
        this.data3done = false;
        CommonListJSONParser commonListJSONParser = new CommonListJSONParser();
        commonListJSONParser.setOnJSONCompletedListener(new CommonListJSONParser.OnJSONCompletedListener() {
            public void OnJSONCompleted(CommonList commonList) {
                Commons.LogDebug("", commonList.toString());
                CommonsDataHandler.this.versionJSONResult(commonList);
            }
        });
        commonListJSONParser.setOnJSONFailedListener(new CommonListJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                Commons.CommonsListRequireUpdate = true;
                boolean unused = CommonsDataHandler.this.data1fail = true;
                if (CommonsDataHandler.this.onFailedToLoadListener != null) {
                    CommonsDataHandler.this.onFailedToLoadListener.onFailed(runnable, exc);
                }
                CommonsDataHandler.this.versionJSONResult((CommonList) null);
            }
        });
        commonListJSONParser.loadXML(this.context.getString(R.string.url_common_list) + "?type=version");
    }

    public void load_mlist() {
        this.data4done = false;
        GenericJSONParser genericJSONParser = new GenericJSONParser(FuturesList_Result.class);
        genericJSONParser.setOnJSONCompletedListener(new GenericJSONParser.OnJSONCompletedListener<FuturesList_Result>() {
            public void OnJSONCompleted(FuturesList_Result futuresList_Result) {
                Commons.LogDebug("", futuresList_Result.toString());
                CommonsDataHandler.this.mlistdataResult(futuresList_Result);
            }
        });
        genericJSONParser.setOnJSONFailedListener(new GenericJSONParser.OnJSONFailedListener() {
            public void OnJSONFailed(Runnable runnable, Exception exc) {
                Commons.LogDebug("OnJSONFailedListener", exc.getMessage());
                boolean unused = CommonsDataHandler.this.data4fail = true;
                if (CommonsDataHandler.this.onFailedToLoadListener != null) {
                    CommonsDataHandler.this.onFailedToLoadListener.onFailed(runnable, exc);
                }
            }
        });
        genericJSONParser.loadXML(this.context.getString(R.string.url_futures) + "&type=MList");
    }

    public void mlistdataResult(FuturesList_Result futuresList_Result) {
        Commons.futures_mlist = futuresList_Result;
        try {
            load_plist(Commons.futures_mlist.getMainData()[0].getMid());
        } catch (Exception e) {
            e.printStackTrace();
            Commons.futures_plist = null;
            this.data4done = true;
            if (this.data1done && this.data2done && this.data3done && this.data4done && this.onDataCompletedListener != null) {
                this.onDataCompletedListener.onDataCompleted();
            }
        }
    }

    public void optionsListJSONResult(CommonList commonList) {
        Commons.commonList = commonList;
        if (commonList != null) {
            Commons.defaultStockCode = commonList.getstockcode();
            Commons.defaultStockName = Commons.language.equals("en_US") ? commonList.getstockname() : commonList.getstocknmll();
            Commons.defaultUnderlyingCode = commonList.getunderlyingcode();
        }
        this.optionsList_done = true;
        if (this.indexoptionsList_done && this.optionsList_done) {
            this.data1done = true;
        }
        if (!this.data1fail && !this.data2fail && !this.data3fail && !this.data4fail) {
            Commons.CommonsListRequireUpdate = false;
        }
        if (this.data1done && this.data2done && this.data3done && this.data4done && this.onDataCompletedListener != null) {
            this.onDataCompletedListener.onDataCompleted();
        }
    }

    public void plistdataResult(FuturesPList_Result futuresPList_Result) {
        this.data4done = true;
        if (!this.data1fail && !this.data2fail && !this.data3fail && !this.data4fail) {
            Commons.CommonsListRequireUpdate = false;
        }
        if (this.data1done && this.data2done && this.data3done && this.data4done && this.onDataCompletedListener != null) {
            this.onDataCompletedListener.onDataCompleted();
        }
        Commons.futures_plist = futuresPList_Result;
    }

    public void setOnDataCompletedListener(OnDataCompletedListener onDataCompletedListener2) {
        this.onDataCompletedListener = onDataCompletedListener2;
    }

    public void setOnFailedToLoadListener(OnFailedToLoadListener onFailedToLoadListener2) {
        this.onFailedToLoadListener = onFailedToLoadListener2;
    }

    public void startLoad() {
        loadOptionsListJSON();
        loadCommonsListJSON();
        loadVersionJSON();
        load_mlist();
    }

    public void versionJSONResult(CommonList commonList) {
        if (commonList != null) {
            String str = commonList.getandroid();
            try {
                if (Float.parseFloat(str) > Float.parseFloat(this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName)) {
                    if (this.context instanceof MasterActivity) {
                        ((MasterActivity) this.context).VersionMessageBox(String.format(this.context.getString(R.string.updatedversion), new Object[]{str}));
                    } else {
                        ((MasterFragmentActivity) this.context).VersionMessageBox(String.format(this.context.getString(R.string.updatedversion), new Object[]{str}));
                    }
                }
            } catch (Exception e) {
                this.data3fail = true;
            }
        }
        this.data3done = true;
        if (!this.data1fail && !this.data2fail && !this.data3fail && !this.data4fail) {
            Commons.CommonsListRequireUpdate = false;
        }
        if (this.data1done && this.data2done && this.data3done && this.data4done && this.onDataCompletedListener != null) {
            this.onDataCompletedListener.onDataCompleted();
        }
    }
}
