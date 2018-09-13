# 简介
对图片进行手指画圈定位

这是一个对特定场景的库，可能单单看简单的描述无法知道它的应用场景，这里举个例子你就能明白了。  
我见到这个应用场景是在公司合作的第三方平台提供的库里面，通过在车的模型图片上画圈，来获取画圈部位的车的配件名称列表，效果如下图：  

![在车模型图片上画圈定位配件](./README_Res/car_draw_cir.gif)  

# 本库实现功能

翻看了下上面对车画圈定位的库，在一堆堆代码混淆中晕晕乎乎好久终于找到，找到后迎来的却是大失所望。内部实现方式完全没有拓展性，同时实现采用的逻辑
也使得定位十分初略（不精准）。于是决定自己动手，衣食都有！  

目前本库实现的功能有：  

* 模型图片的部件采用 **锚点** 和 **锚点区域** 两种方式；
* **锚点** 时，通过判断锚点坐标是否在手指画圈轨迹区域内来判定；
* **锚点区域** 时，通过判断锚点区域是否和手指画圈轨迹区域相交来判定；
* 锚点 和 锚点区域 的坐标测量，只需要根据在模型图片上的坐标位置设置就行，不需要考虑实际显示时图片大小；

判定方式图示：  

<img src="./README_Res/maodian1.png"  width="450px" alt="锚点判定方式"/> <img src="./README_Res/maodian2.png"  width="450px" alt="锚点区域判定方式"/>

# 使用步骤

**1. 引入并依赖库**  

引入库的方式：  
**Step 1.** Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
**Step 2.** Add the dependency

	dependencies {
	        implementation 'com.github.OCNYang:DrawPartViewDemo:v1.0.0'
	}

**2. 准备模型图片和确定锚点坐标**

确定你的模型图片，然后根据模型图片的 **宽px像素值为X轴、高px像素值为Y轴、模型图片的左上角为原点、向右和向下为正方向**，通过上述这个坐标系来
确定和测量各锚点坐标、各锚点区域关键点坐标。如下图：  

![蜻蜓坐标系](./README_Res/dragonflyw_spec.png)  
[查看大图](https://raw.githubusercontent.com/OCNYang/DrawPartViewDemo/master/README_Res/dragonflyw_spec.png)

上述蜻蜓图片的大小为（2400px * 1600px），那就以此为坐标系来确定各锚点。这里我在测量坐标时使用的是 [马克鳗](http://www.getmarkman.com/) 这个工具，
如果想省力，直接把这一步交给美工就好了，哈哈~~~  

**3. 在布局中使用 DrawPartView**  

和普通控件相同，直接在布局 xml 文件需要的地方使用 DrawPartView，同时需要说明的是 DrawPartView 并没有提供自定义的属性，都需要
在代码中动态设置。

    <com.ocnyang.drawpartview.DrawPartView
        android:id="@+id/draw_part_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        ......
        />

在代码中设置相关属性：  

    mDrawPartView = ((DrawPartView) findViewById(R.id.draw_part_view));
    
    //加载图片方法一:原生加载，注意当图片过大时，有可能造成内存溢出；
    //mDrawPartView.setImageView(R.drawable.dragonfly, 2400, 1600);

    //加载图片方法二（推荐）：使用框架加载
    mDrawPartView.setImageView(new ImageLoaderInterface() {
        @Override
        public void displayImage(Context context, ImageView imageView) {
            //请使用图片的默认显示方式，不要设置图片 ScaleType 的显示方式。
            Glide.with(context)
                    .load(R.drawable.dragonflyw)
                    .into(imageView);
        }
    },2400,1600);//设置模型图片测量锚点时的宽度和高度

    //设置画圈时手指轨迹的画笔样式
    Paint fingerTrackPaint = new Paint();
    fingerTrackPaint.setColor(Color.BLACK);
    fingerTrackPaint.setStrokeWidth(5);
    fingerTrackPaint.setStyle(Paint.Style.STROKE);
    mDrawPartView.setFingerTrackPaint(fingerTrackPaint);

    //设置覆盖层锚点区域绘制时的画笔样式
    Paint overlayViewPaint = new Paint();
    overlayViewPaint.setColor(0x99ee1111);
    overlayViewPaint.setStrokeWidth(2);
    overlayViewPaint.setStyle(Paint.Style.STROKE);
    mDrawPartView.setOverlayViewPaint(overlayViewPaint);
    
    mDrawPartView.showOverlayView(false);

    //设置画圈结果的监听器
    mDrawPartView.setOnDrawPartResultListener(new DrawPartView.OnDrawPartResultListener() {
        @Override
        public void onDrawPartResult(List<PartPointF> partPointFList) {
            StringBuilder stringBuilder = new StringBuilder("圈中了：");
            for (PartPointF partPointF : partPointFList) {
                stringBuilder.append(partPointF.getPartName()).append(" ");
            }
            Toast.makeText(MainActivity.this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
        }
    });

**4. 设置锚点区域坐标集**  

根据上面第 1 步准备好的模型图片和测量坐标，来对 DrawPartView 设置锚点坐标 & 锚点区域。  
设置时有几点需要值得注意，必须遵从否则可能达不到预期的效果：  

1. 调用 `DrawPartView.setPartPointFList(partPointFList)` 方法时必须保证 DrawPartView 已经测量完毕，[具体方法参考](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/0802/1641.html)；
2. 设置锚点坐标时使用 PartPointF 实体类设置点坐标；
3. 设置锚点区域时使用 PartPathPointF 实体类设置区域范围 Path，此时设置的点坐标没有意义；
4. 设置锚点和锚点区域时，关键点的坐标值和长度值需要使用 `DrawPartView` 提供的一系列 `getRealShowXXX()` 方法处理后的值。

使用方法参考代码：  

    //设置监听 DrawPartView 测量完成的回调方法，保证是在 DrawPartView 测量完毕后设置锚点集
    mDrawPartView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT >= 16) {
                //去除监听，保证只回调一次，防止多次设置锚点集
                mDrawPartView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            } else {
                mDrawPartView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
            initDragonflyView();
        }
    });

    /**
     * 根据模型图片测量的坐标设置锚点集
     */
    private void initDragonflyView() {
        ArrayList<PartPointF> partPointFList = new ArrayList<>();

        //设置锚点区域
        Path path1 = new Path();
        path1.addOval(mDrawPartView.getRealShowRectF(1143f, 198f, 1263f, 398f), Path.Direction.CCW);
        partPointFList.add(new PartPathPointF(0, 0, 1, "头", path1));//设置锚点区域时，点坐标 x,y 没有意义，这里直接写成 0。

        Path path2_1 = new Path();
        Path path2_2 = new Path();
        path2_1.addCircle(mDrawPartView.getRealShowX(1142f), mDrawPartView.getRealShowY(311), mDrawPartView.getRealShowLength(77), Path.Direction.CCW);
        path2_2.addCircle(mDrawPartView.getRealShowX(1272f), mDrawPartView.getRealShowY(311), mDrawPartView.getRealShowLength(77), Path.Direction.CCW);
        path2_1.op(path2_2, Path.Op.XOR);
        partPointFList.add(new PartPathPointF(0, 0, 2, "眼睛", path2_1));

        ......

        Path path6 = new Path();
        path6.moveTo(mDrawPartView.getRealShowX(1302f), mDrawPartView.getRealShowY(527));
        path6.lineTo(mDrawPartView.getRealShowX(1286f), mDrawPartView.getRealShowY(591f));
        path6.lineTo(mDrawPartView.getRealShowX(1397f), mDrawPartView.getRealShowY(760f));
        path6.lineTo(mDrawPartView.getRealShowX(1703f), mDrawPartView.getRealShowY(965f));
        path6.lineTo(mDrawPartView.getRealShowX(1769f), mDrawPartView.getRealShowY(941f));
        path6.lineTo(mDrawPartView.getRealShowX(1670f), mDrawPartView.getRealShowY(750f));
        path6.close();
        partPointFList.add(new PartPathPointF(0, 0, 6, "右下翼", path6));

        ......
        
        //设置锚点坐标
        partPointFList.add(new PartPointF(mDrawPartView.getRealShowX(630f), mDrawPartView.getRealShowY(938f), 12, "左下翼尖"));

        mDrawPartView.setPartPointFList(partPointFList);
    }

到此整个使用流程已经完成，更具体的使用细节请参考 Demo [源码](https://github.com/OCNYang/DrawPartViewDemo/blob/master/app/src/main/java/com/ocnyang/drawpartviewdemo/MainActivity.java)。