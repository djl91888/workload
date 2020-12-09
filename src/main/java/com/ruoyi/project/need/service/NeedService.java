package com.ruoyi.project.need.service;
import com.ruoyi.project.need.domain.Export;
import com.ruoyi.project.need.domain.Need;
import com.ruoyi.project.system.role.domain.Role;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 需求 服务层
 * 
 * @author cty
 */
public interface NeedService
{

    List<Role> selectRole(Long userId);

    int NeedAddList(Need need);

    List<Need> NeedType();

    List<Need> NeedSources();

    List<Need> NeedOwner();

    int NeedAddWorkload(Need need);

    int  NeedAddUser(Need need);

    List<Need> NeedMyLists(String demandContent,String cqNum,String createStartTime,String createEndTime,String demandTypeId,String demandStatus,String createrId,String sourceBy);;

    List<Need> NeedMyList(Need need);

    List<Need> needCreator();

    List<Need> NeedMycreater(Need need);

    List<Need> NeedContactList(Need need);

    List<Export> selectNeedList(Need need);

    List<Need> needShowUpdate(String demandId);

    List<Need> needShowPepole(String demandId);

    String needShowXg(String demandId);

    int NeedUpdateList(Need need);

    int NeedUpdateUser(Need need);

    int NeedUpdateWorkload(Need need);

    List<Need> Needothercreater(Need need);

    List<Need> needContact();

    List<Need> needUsername(String userid);

    int selectBj(String demandId);

    int deleteBj(String demandId);
}
