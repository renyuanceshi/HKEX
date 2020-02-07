package com.hkex.soma.element;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hkex.soma.R;
import com.hkex.soma.activity.DividendSearch;
import com.hkex.soma.activity.Education;
import com.hkex.soma.activity.MarginTable;
import com.hkex.soma.activity.MarketSnapshot;
import com.hkex.soma.activity.OptionClassList;
import com.hkex.soma.activity.OptionsCalculatorSearch;
import com.hkex.soma.activity.OptionsCombo;
import com.hkex.soma.activity.OptionsVolume;
import com.hkex.soma.activity.Portfolio;
import com.hkex.soma.activity.Portfolio_land;
import com.hkex.soma.activity.ProductMainpage;
import com.hkex.soma.activity.Search;
import com.hkex.soma.activity.SearchPage;
import com.hkex.soma.activity.Seminar;
import com.hkex.soma.activity.SettingPage;
import com.hkex.soma.activity.Video;
import com.hkex.soma.basic.MasterFragmentActivity;
import com.hkex.soma.slideAnimator.SlideLeftAnimationHandler;
import com.hkex.soma.utils.Commons;

public class MenuContainer extends LinearLayout {
    private Context context;
    private View menu;
    private SlideLeftAnimationHandler sliedBackHandler = null;

    public MenuContainer(Context context2) {
        super(context2);
        this.context = context2;
        this.menu = ((LayoutInflater) context2.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu, this, false);
        addView(this.menu);
        init();
    }

    private void init() {
        ((RelativeLayout) this.menu.findViewById(R.id.searchBar)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Commons.noResumeAction = true;
                Intent intent = new Intent();
                intent.setClass(MenuContainer.this.context, SearchPage.class);
                intent.putExtra("typecode", -1);
                intent.putExtra("isAutocomplete", true);
                MenuContainer.this.context.startActivity(intent);
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_row01)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MenuContainer.this.context.getClass() != MarketSnapshot.class) {
                    ((MasterFragmentActivity) MenuContainer.this.context).dataLoading();
                    Intent intent = new Intent();
                    intent.setClass(MenuContainer.this.context, MarketSnapshot.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    MenuContainer.this.context.startActivity(intent);
                } else if (MenuContainer.this.sliedBackHandler != null) {
                    MenuContainer.this.sliedBackHandler.onClick((View) null);
                }
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_row02)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MenuContainer.this.context.getClass() != Portfolio.class) {
                    ((MasterFragmentActivity) MenuContainer.this.context).dataLoading();
                    Intent intent = new Intent();
                    intent.setClass(MenuContainer.this.context, Portfolio_land.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    MenuContainer.this.context.startActivity(intent);
                } else if (MenuContainer.this.sliedBackHandler != null) {
                    MenuContainer.this.sliedBackHandler.onClick((View) null);
                }
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_row03)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MenuContainer.this.context.getClass() != Search.class) {
                    ((MasterFragmentActivity) MenuContainer.this.context).dataLoading();
                    Intent intent = new Intent();
                    intent.setClass(MenuContainer.this.context, Search.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    MenuContainer.this.context.startActivity(intent);
                } else if (MenuContainer.this.sliedBackHandler != null) {
                    MenuContainer.this.sliedBackHandler.onClick((View) null);
                }
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_row04)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MenuContainer.this.context.getClass() != Video.class) {
                    ((MasterFragmentActivity) MenuContainer.this.context).dataLoading();
                    Intent intent = new Intent();
                    intent.setClass(MenuContainer.this.context, Video.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    MenuContainer.this.context.startActivity(intent);
                } else if (MenuContainer.this.sliedBackHandler != null) {
                    MenuContainer.this.sliedBackHandler.onClick((View) null);
                }
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_row05)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MenuContainer.this.context.getClass() != Education.class) {
                    ((MasterFragmentActivity) MenuContainer.this.context).dataLoading();
                    Intent intent = new Intent();
                    intent.setClass(MenuContainer.this.context, Education.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    MenuContainer.this.context.startActivity(intent);
                } else if (MenuContainer.this.sliedBackHandler != null) {
                    MenuContainer.this.sliedBackHandler.onClick((View) null);
                }
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_row06)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MenuContainer.this.context.getClass() != OptionClassList.class) {
                    ((MasterFragmentActivity) MenuContainer.this.context).dataLoading();
                    Intent intent = new Intent();
                    intent.setClass(MenuContainer.this.context, OptionClassList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    MenuContainer.this.context.startActivity(intent);
                } else if (MenuContainer.this.sliedBackHandler != null) {
                    MenuContainer.this.sliedBackHandler.onClick((View) null);
                }
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_row07)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MenuContainer.this.context.getClass() != MarginTable.class) {
                    ((MasterFragmentActivity) MenuContainer.this.context).dataLoading();
                    Intent intent = new Intent();
                    intent.setClass(MenuContainer.this.context, MarginTable.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    MenuContainer.this.context.startActivity(intent);
                } else if (MenuContainer.this.sliedBackHandler != null) {
                    MenuContainer.this.sliedBackHandler.onClick((View) null);
                }
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_row08)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MenuContainer.this.context.getClass() != DividendSearch.class) {
                    ((MasterFragmentActivity) MenuContainer.this.context).dataLoading();
                    Intent intent = new Intent();
                    intent.setClass(MenuContainer.this.context, DividendSearch.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    MenuContainer.this.context.startActivity(intent);
                } else if (MenuContainer.this.sliedBackHandler != null) {
                    MenuContainer.this.sliedBackHandler.onClick((View) null);
                }
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_row09)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((MasterFragmentActivity) MenuContainer.this.context).dataLoading();
                Intent intent = new Intent();
                intent.setClass(MenuContainer.this.context, SettingPage.class);
                MenuContainer.this.context.startActivity(intent);
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_row10)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MenuContainer.this.context.getClass() != OptionsCalculatorSearch.class) {
                    ((MasterFragmentActivity) MenuContainer.this.context).dataLoading();
                    Intent intent = new Intent();
                    intent.setClass(MenuContainer.this.context, OptionsCalculatorSearch.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    MenuContainer.this.context.startActivity(intent);
                } else if (MenuContainer.this.sliedBackHandler != null) {
                    MenuContainer.this.sliedBackHandler.onClick((View) null);
                }
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_row11)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MenuContainer.this.context, OptionsCombo.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                MenuContainer.this.context.startActivity(intent);
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_row12)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MenuContainer.this.context.getClass() != OptionsVolume.class) {
                    ((MasterFragmentActivity) MenuContainer.this.context).dataLoading();
                    Intent intent = new Intent();
                    intent.setClass(MenuContainer.this.context, OptionsVolume.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    MenuContainer.this.context.startActivity(intent);
                } else if (MenuContainer.this.sliedBackHandler != null) {
                    MenuContainer.this.sliedBackHandler.onClick((View) null);
                }
            }
        });
        ((RelativeLayout) this.menu.findViewById(R.id.menu_ProductMainpage)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MenuContainer.this.context.getClass() != Seminar.class) {
                    ((MasterFragmentActivity) MenuContainer.this.context).dataLoading();
                    Intent intent = new Intent();
                    intent.setClass(MenuContainer.this.context, ProductMainpage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    MenuContainer.this.context.startActivity(intent);
                } else if (MenuContainer.this.sliedBackHandler != null) {
                    MenuContainer.this.sliedBackHandler.onClick((View) null);
                }
            }
        });
    }

    public void setSliedBackHandler(SlideLeftAnimationHandler slideLeftAnimationHandler) {
        this.sliedBackHandler = slideLeftAnimationHandler;
    }
}
