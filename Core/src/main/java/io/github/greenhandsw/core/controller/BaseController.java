package io.github.greenhandsw.core.controller;

import io.github.greenhandsw.core.vo.BaseVO;

import java.io.Serializable;

public interface BaseController<VO extends BaseVO<ID>, ID extends Serializable, ENTITYID extends Serializable> {

}
