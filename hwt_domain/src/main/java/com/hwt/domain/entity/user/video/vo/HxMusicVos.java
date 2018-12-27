package com.hwt.domain.entity.user.video.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HxMusicVos implements Serializable {

    private List<HxMusicVo> hxMusicVos = new ArrayList<>();

    public List<HxMusicVo> getHxMusicVos() {
        return hxMusicVos;
    }

    public void setHxMusicVos(List<HxMusicVo> hxMusicVos) {
        this.hxMusicVos = hxMusicVos;
    }
}
