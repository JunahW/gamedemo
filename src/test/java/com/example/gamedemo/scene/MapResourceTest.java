package com.example.gamedemo.scene;

import com.example.gamedemo.common.resource.ResourceManager;
import com.example.gamedemo.common.utils.ExcelUtils;
import com.example.gamedemo.common.utils.JsonUtils;
import com.example.gamedemo.server.game.npc.resource.NpcResource;
import com.example.gamedemo.server.game.scene.resource.LandformResource;
import com.example.gamedemo.server.game.scene.resource.MapResource;
import com.example.gamedemo.server.game.scene.service.SceneManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author wengj
 * @description
 * @date 2019/5/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapResourceTest {
  @Autowired private SceneManager sceneManager;

  @Test
  public void testSceneImportExcel() throws Exception {
    Map<Object, MapResource> resourceMap = ResourceManager.getResourceMap(MapResource.class);
    System.out.println(resourceMap);
  }

  @Test
  public void testLandformImportExcel() throws Exception {
    Map<Object, LandformResource> resourceMap =
        ResourceManager.getResourceMap(LandformResource.class);
    System.out.println(resourceMap);
  }

  @Test
  public void testNpcImportExcel() throws Exception {
    List<NpcResource> npcResourceList = ExcelUtils.importExcel(NpcResource.class);
    System.out.println(npcResourceList);
    System.out.println(sceneManager);
  }

  @Test
  public void testJson() {
    String jsonString =
        "{\"sceneId\":\"s2001\",\"sceneName\":\"区庄\",\"neighbors\":\"s2002\",\"width\":15,\"height\":6,\"x\":0,\"y\":0,\"sceneMap\":[[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,0,0,1,1,1,1,1,1,1],[1,1,1,1,1,1,0,0,1,1,1,1,1,1,1],[1,1,1,1,1,1,0,1,0,0,1,1,1,1,1],[1,1,1,1,0,0,0,0,1,0,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]],\"npcs\":\"n2001,n2002\",\"monsters\":\"m1001,m2002\",\"playerSet\":[],\"npcResourceSet\":[],\"id\":\"s2001\"}";

    MapResource mapResource = JsonUtils.deSerializeEntity(jsonString, MapResource.class);

    System.out.println(mapResource);
  }
}
