package com.ruoyi.project.need.service;


import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.project.need.domain.*;
import com.ruoyi.project.need.mapper.NeedMapper;
import com.ruoyi.project.system.role.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 需求 服务层实现
 * 
 * @author cty
 * @date 2020-11-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NeedServiceImpl implements NeedService
{
    private static Logger logger = LoggerFactory.getLogger(NeedServiceImpl.class);

    @Autowired
    NeedMapper needmapper;
    @Override
    public List<Role> selectRole(Long userId) {
        return needmapper.selectRole(userId);
    }



    @Override
    public int NeedAddList(Need need){
        String demandDesc = need.getDemandDesc();
        String demandContent = need.getDemandContent();
        String regex = "[\\s\\\\/:\\*\\?\\\"<>\\|]";// 需求的名称正则表达式写法
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(demandContent);
        boolean b = matcher.find();
        if(b){
            return 777;
        }
        //去除空格
        demandContent = demandContent.replace(" ", "");
        need.setDemandContent(demandContent);
        if(StringUtils.isEmpty(need.getCreateTime()) || StringUtils.isEmpty(need.getDemandContent()) || StringUtils.isEmpty(need.getSourceId())   || StringUtils.isEmpty(need.getDemandStatus()) || StringUtils.isEmpty(need.getCreaterId())){
            return 0;
        }

        if(!StringUtils.isEmpty(demandDesc) && demandDesc.length()>500){
            return 999;
        }
        return needmapper.NeedAddList(need);


    }

    @Override
    public List<Need> NeedType() {
        return needmapper.NeedType();
    }

    @Override
    public List<Need> NeedSources() {
        return needmapper.NeedSources();
    }

    @Override
    public List<Need> NeedOwner() {
        return needmapper.NeedOwner();
    }

    @Override
    public int NeedAddWorkload(Need need) {
        String PerSon = need.getPerSon();//负责人
        int i=0;
        if(!StringUtils.isEmpty(PerSon)){
            Work work= new Work();

            //String ll="103-1,102-10,101-0";
            String[] split = PerSon.split(",");
            work.setDemandId(need.getDemandId());//需求id
            work.setCreaterId(need.getCreaterId());//创建人id
            work.setCreaterName(need.getCreaterName());//创建人姓名
            work.setCreateTime(need.getCreateTime());//创建时间
            for (String s : split) {
                String[] split1 = s.split("-");
                String roleid = split1[1];//拿出角色id
                //到工作量模型里查询数据
                List<WorkLoad> list=needmapper.selectModel(roleid);
                //数据入模型表
                if(list!=null && list.size()>0){
                    for (WorkLoad w:list)
                    {
                        work.setWorkCategoryId(w.getModelType());//工作大类类型
                        work.setWorkTypeId(w.getWorkType());//工作小类
                        work.setWorkModelId(w.getModelId());//工作模型id
                        work.setRoleId(roleid);//角色id
                        //插入模型表
                        i=needmapper.NeedAddWorkload(work);

                    }

                }

            }
        }


        return i;

    }

    @Override
    public int NeedAddUser(Need need) {
        String PerSon = need.getPerSon();//负责人
        int i=0;
        if(!StringUtils.isEmpty(PerSon)){
            WkDemandEmployee wk=new WkDemandEmployee();
            wk.setDemandId(need.getDemandId());
            wk.setRelevant(need.getRelevant());
            wk.setCreaterId(need.getCreaterId());
            wk.setCreaterName(need.getCreaterName());
            wk.setCreateTime(need.getCreateTime());

            //String ll="103-1,102-10,101-0";
            String[] split = PerSon.split(",");
            for (String s : split) {
                String[] split1 = s.split("-");
                String userid=split1[0];
                String roleid=split1[1];
                //need重新赋值
                wk.setUserId(userid);
                wk.setRoleId(roleid);
                i=needmapper.NeedAddUser(wk);

            }

        }

        return i;



    }

    @Override
    public List<Need> NeedMyLists(String demandContent,String cqNum,String createStartTime,String createEndTime,String demandTypeId,String demandStatus,String createrId,String sourceBy)
    {
        List<Need> needs = needmapper.NeedMyLists(demandContent,cqNum,createStartTime,createEndTime,demandTypeId,demandStatus,createrId,sourceBy);
        //全部需求的主管可以编辑需求
        for(Need n:needs){
            n.setEditStatus("1");
        }

        return needs;
    }

    @Override
    public List<Need> NeedMyList(Need need) {
        return needmapper.NeedMyList(need);
    }


    @Override
    public List<Need> needCreator() {
        return needmapper.needCreator();
    }

    @Override
    public List<Need> NeedMycreater(Need need) {

        return needmapper.NeedMycreater(need);
    }

    @Override
    public List<Need> NeedContactList(Need need) {
        return needmapper.NeedContactList(need);
    }

    @Override
    public List<Export> selectNeedList(Need need) {
        return needmapper.selectNeedList(need);
    }

    @Override
    public List<Need> needShowUpdate(String demandId) {
        return needmapper.needShowUpdate(demandId);
    }

    @Override
    public List<Need> needShowPepole(String demandId) {
        return needmapper.needShowPepole(demandId);
    }

    @Override
    public String needShowXg(String demandId) {
        return needmapper.needShowXg(demandId);
    }

    @Override
    public int NeedUpdateList(Need need) {
        String demandDesc = need.getDemandDesc();
        String demandContent = need.getDemandContent();
        String regex = "[\\s\\\\/:\\*\\?\\\"<>\\|]";// 需求的名称正则表达式写法
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(demandContent);
        boolean b = matcher.find();
        if(b){
            return 777;
        }
        //去除空格
        demandContent = demandContent.replace(" ", "");
        need.setDemandContent(demandContent);
        if(StringUtils.isEmpty(need.getCreateTime()) || StringUtils.isEmpty(need.getDemandContent()) ||
                StringUtils.isEmpty(need.getSourceId())   || StringUtils.isEmpty(need.getDemandStatus())
                ){
            return 0;
        }
        if(!StringUtils.isEmpty(demandDesc) && demandDesc.length()>500 ){
            return 999;
        }

        return needmapper.NeedUpdateList(need);


    }

    @Override
    public int NeedUpdateUser(Need need) {
        Integer i=0;
        Integer j=0;
        List<Need> list=null;
        String PerSon = need.getPerSon();//负责人
        String demandId=need.getDemandId();//需求id
        WkDemandEmployee wk=new WkDemandEmployee();

        if(!StringUtils.isEmpty(demandId)){
            list=needmapper.listSeach(demandId);
            for (Need n:list) {
                wk.setCreaterId(n.getCreaterId());
                wk.setCreaterName(n.getCreaterName());

            }

        }
        if(!StringUtils.isEmpty(PerSon)){


            wk.setDemandId(demandId);
            wk.setCreateTime(need.getCreateTime());
            wk.setRelevant(need.getRelevant());

            //String ll="103-1,102-10,101-0";
            String[] split = PerSon.split(",");
            //插之前就删除原来需求的数据
           Integer t= needmapper.deleteNeedUser(demandId);
           if(t>0){
               logger.info("清空WK_DEMAND_EMPLOYEE成功了！");
               for (String s : split) {
                   String[] split1 = s.split("-");
                   String userid=split1[0];
                   String roleid=split1[1];
                   //need重新赋值
                   wk.setUserId(userid);
                   wk.setRoleId(roleid);
                   j=needmapper.NeedAddUser(wk);//新增
                   if(j>0){
                       j=j+0;
                   }

               }
               if(j>0){
                   return j;
               }
           }


        }
        return 0;

    }

    @Override
    public int NeedUpdateWorkload(Need need) {
        int i=0;
        String PerSon = need.getPerSon();//负责人
        if(!StringUtils.isEmpty(PerSon)) {
            Work work= new Work();
            //String ll="103-1,102-10,101-0";
            String[] split = PerSon.split(",");
            work.setDemandId(need.getDemandId());//需求id
            work.setCreaterId(need.getCreaterId());//创建人id
            work.setCreaterName(need.getCreaterName());//创建人姓名
            work.setCreateTime(need.getCreateTime());//创建时间
            for (String s : split) {
                String[] split1 = s.split("-");
                String roleid = split1[1];//拿出角色id
                //到工作量里查询数据
                List<Work> list=needmapper.selectWorkModel(work);
                //如果存在则跳过添加
                if(list!=null && list.size()>0){
                    continue;

                }
                List<WorkLoad> workLoad=needmapper.selectModel(roleid);
                for (WorkLoad w:workLoad)
                {
                    work.setWorkCategoryId(w.getModelType());//工作大类类型
                    work.setWorkTypeId(w.getWorkType());//工作小类
                    work.setWorkModelId(w.getModelId());//工作模型id
                    work.setRoleId(roleid);//角色id
                    //插入模型表
                    i=needmapper.NeedAddWorkload(work);

                }

            }

        }

        return i;

    }

    @Override
    public List<Need> Needothercreater(Need need) {
        return needmapper.Needothercreater(need);
    }

    @Override
    public List<Need> needContact() {
        return needmapper.needContact();
    }

    @Override
    public List<Need> needUsername(String userid) {
        return needmapper.needUsername(userid);
    }

    @Override
    public int selectBj(String demandId) {
        return needmapper.selectBj(demandId);
    }

    @Override
    public int deleteBj(String demandId) {
        return needmapper.deleteBj(demandId);
    }


}
