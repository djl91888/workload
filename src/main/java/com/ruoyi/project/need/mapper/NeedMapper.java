package com.ruoyi.project.need.mapper;

import com.ruoyi.project.need.domain.*;
import com.ruoyi.project.system.role.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 需求 数据层
 * 
 * @author cty
 */
public interface NeedMapper
{

    List<Role> selectRole(Long userId);



    int NeedAddList(Need need);

    List<Need> NeedType();

    List<Need> NeedSources();

    List<Need> NeedOwner();

    List<Need> NeedMyLists(@Param("demandContent") String demandContent,@Param("cqNum") String cqNum,@Param("createStartTime") String createStartTime,@Param("createEndTime") String createEndTime,@Param("demandTypeId") String demandTypeId,@Param("demandStatus") String demandStatus,@Param("createrId") String createrId,@Param("sourceBy") String sourceBy);
    List<Need> NeedMyList(Need need);

    Integer NeedAddUser(WkDemandEmployee wk);

    List<WorkLoad> selectModel(String roleid);

    int NeedAddWorkload(Work work);

    List<Need> needCreator();

    List<Need> NeedMycreater(Need need);

    List<Need> NeedContactList(Need need);

    List<Export> selectNeedList(Need need);

    List<Need> needShowUpdate(String demandId);

    String needShowXg(String demandId);

    List<Need> needShowPepole(String demandId);

    int NeedUpdateList(Need need);

    int NeedUpdateUser(WkDemandEmployee wk);

    List<Work> selectWorkModel(Work work);

    List<Need> Needothercreater(Need need);

    Integer deleteNeedUser(String demandId);

    Map<String,String> selectNeed(@Param("demandid") String demandid);
    
    List<Need> listSeach(String demanid);

    String selectBtypename(String modelid);

    Map<String,String> selectStypename(@Param("modelid") String modelid);

    List<Need> needContact();

    List<Need> needUsername(String userid);

    int selectBj(String demandId);

    int deleteBj(String demandId);
}