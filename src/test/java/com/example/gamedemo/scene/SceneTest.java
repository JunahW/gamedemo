package com.example.gamedemo.scene;

import com.example.gamedemo.server.common.utils.ExcelUtils;
import com.example.gamedemo.server.game.scene.model.Map;
import com.example.gamedemo.server.game.scene.model.Npc;
import com.example.gamedemo.server.game.scene.model.Scene;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wengj
 * @description
 * @date 2019/5/7
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SceneTest {

    @Test
    public void testSceneImportExcel() throws Exception {
        List<Scene> scenes = ExcelUtils.importExcel(Scene.class);
        System.out.println(scenes);

    }

    @Test
    public void testNpcImportExcel() throws Exception {
        List<Npc> npcList = ExcelUtils.importExcel(Npc.class);
        System.out.println(npcList);

    }

    @Test
    public void testMapImportExcel() throws Exception {
        List<Map> mapList = ExcelUtils.importExcel(Map.class);
        System.out.println(mapList);

    }

}
