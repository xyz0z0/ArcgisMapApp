package xyz.xyz0z0.arcgismapapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.esri.arcgisruntime.geometry.*
import com.esri.arcgisruntime.layers.ArcGISMapImageLayer
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.mapping.view.MapView
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol

class SimpleMapActivity : AppCompatActivity() {

    private val mSimpleMapUrl = "http://services.arcgisonline.com/ArcGIS/rest/services/World_Imagery/MapServer"
    private lateinit var mMapView: MapView
    private lateinit var btnAddPoint: Button
    private lateinit var btnAddLine: Button
    private lateinit var btnAddArea: Button


    private lateinit var mArcGISMap: ArcGISMap
    private lateinit var graphicsOverlay: GraphicsOverlay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_map)
        findView()
        initView()
        initMap()
    }

    private fun initMap() {
        // 不显示 arcgis 标识
        mMapView.isAttributionTextVisible = false
        // map 管理类
        mArcGISMap = ArcGISMap()
        mMapView.map = mArcGISMap
        // 新建图层
        val baseMapLayer = ArcGISMapImageLayer(mSimpleMapUrl)
        baseMapLayer.isVisible = true
        // 添加图层
        mArcGISMap.basemap.baseLayers.add(baseMapLayer)
        // 初始化可见区域
        val center = Point(117.289238, 31.86692, SpatialReference.create(4326))
        mArcGISMap.initialViewpoint = Viewpoint(center, (1_000_00).toDouble())
        // 自定义添加控件的图层
        graphicsOverlay = GraphicsOverlay()
        mMapView.graphicsOverlays.add(graphicsOverlay)
    }

    private fun initView() {
        btnAddPoint.setOnClickListener {
            val marker1: SimpleMarkerSymbol = SimpleMarkerSymbol(SimpleMarkerSymbol.Style.DIAMOND, Color.RED, 10f)
            val point1 = Point(117.289041, 31.866674, SpatialReference.create(4326))
            val graphic1 = Graphic(point1, marker1)
            graphicsOverlay.graphics.add(graphic1)
        }
        btnAddLine.setOnClickListener {
            val borderCAtoNV = PointCollection(SpatialReferences.getWgs84())
            borderCAtoNV.add(117.289041, 31.866674)
            borderCAtoNV.add(117.289141, 31.867674)
            borderCAtoNV.add(117.289941, 31.868674)
            val lineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 3F)
            val graphic1 = Graphic(Polyline(borderCAtoNV), lineSymbol)
            graphicsOverlay.graphics.add(graphic1)
        }
        btnAddArea.setOnClickListener {
            val lineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 3F)
            val fillSymbol = SimpleFillSymbol(SimpleFillSymbol.Style.CROSS, Color.BLUE, lineSymbol)
            val coloradoCorners = PointCollection(SpatialReferences.getWgs84())
            coloradoCorners.add(117.289041, 31.866674)
            coloradoCorners.add(117.299041, 31.866674)
            coloradoCorners.add(117.299041, 31.856674)
            coloradoCorners.add(117.289041, 31.856674)
            val graphic1 = Graphic(Polygon(coloradoCorners), fillSymbol)
            graphicsOverlay.graphics.add(graphic1)
        }
    }

    private fun findView() {
        mMapView = findViewById(R.id.mvSimple)
        btnAddPoint = findViewById(R.id.btnAddPoint)
        btnAddLine = findViewById(R.id.btnAddLine)
        btnAddArea = findViewById(R.id.btnAddArea)
    }

    override fun onPause() {
        super.onPause()
        mMapView.pause()
    }

    override fun onResume() {
        super.onResume()
        mMapView.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.dispose()
    }
}