# -*- coding: utf-8 -*-

from pywinauto import application
import pywinauto
import json

app = application.Application().connect(title_re=u"中国电子口岸客户端.*")
dlg = app.window_(title_re = u'中国电子口岸客户端.*')
try:
    dlg["TabControl"].TypeKeys("^{TAB}")
    dlg["TabControl"].TypeKeys("^{TAB}")
    dlg["TabControl"].TypeKeys("^{TAB}")
    dlg["TabControl"].TypeKeys("^{TAB}")
except:
    pass
ctrls = pywinauto.application._resolve_control(dlg.criteria)
dialog_controls = ctrls[-1].Children()
name_control_map = pywinauto.findbestmatch.build_unique_dict(dialog_controls)
c={}
for ctrlname,ctrl in name_control_map.items():
    if ctrl.friendlyclassname=='Edit':
        c[ctrlname]=ctrl
    elif ctrl.friendlyclassname=='ListView':
        c[ctrlname]=ctrl

hthead={}
hthead[u'企业内部编号']     = c['Edit29'].Texts()[0]
hthead[u'电子手册编号']     = c['Edit30'].Texts()[0]
hthead[u'手册类型']         = c['Edit8'].Texts()[0]
hthead[u'主管海关']         = c['Edit37'].Texts()[0]
hthead[u'主管外经贸']       = c['Edit41'].Texts()[0]
hthead[u'收货地区']         = c['Edit38'].Texts()[0]
hthead[u'经营单位']         = c['Edit28'].Texts()[0]
hthead[u'经营单位名称']     = c['Edit27'].Texts()[0]
hthead[u'加工单位']         = c['Edit26'].Texts()[0]
hthead[u'加工单位名称']     = c['Edit25'].Texts()[0]
hthead[u'外商公司']         = c['Edit22'].Texts()[0]
hthead[u'外商经理人']       = c['Edit20'].Texts()[0]
hthead[u'贸易方式']         = c['Edit35'].Texts()[0]
hthead[u'征免性质']         = c['Edit36'].Texts()[0]
hthead[u'起抵地']           = c['Edit54'].Texts()[0]
hthead[u'成交方式']         = c['Edit58'].Texts()[0]
hthead[u'内销比']           = c['Edit14'].Texts()[0]
hthead[u'协议号']           = c['Edit19'].Texts()[0]
hthead[u'许可证号']         = c['Edit21'].Texts()[0]
hthead[u'批准文号']         = c['Edit24'].Texts()[0]
hthead[u'进口合同']         = c['Edit23'].Texts()[0]
hthead[u'出口合同']         = c['Edit18'].Texts()[0]
hthead[u'备案进口总额']     = c['Edit16'].Texts()[0]
hthead[u'进口币制']         = c['Edit32'].Texts()[0]
hthead[u'备案出口总额']     = c['Edit15'].Texts()[0]
hthead[u'出口币制']         = c['Edit33'].Texts()[0]
hthead[u'加工种类']         = c['Edit31'].Texts()[0]
hthead[u'保税方式']         = c['Edit40'].Texts()[0]
hthead[u'有效日期']         = c['Edit13'].Texts()[0]
hthead[u'进出口岸']         = c['Edit12'].Texts()[0]
hthead[u'进口货物项数']     = c['Edit7'].Texts()[0]
hthead[u'本次进口总额']     = c['Edit11'].Texts()[0]
hthead[u'出口货物项数']     = c['Edit6'].Texts()[0]
hthead[u'本次出口总额']     = c['Edit10'].Texts()[0]
hthead[u'处理标志']         = c['Edit9'].Texts()[0]
hthead[u'管理对象']         = c['Edit5'].Texts()[0]
hthead[u'录入日期']         = c['Edit3'].Texts()[0]
hthead[u'申报日期']         = c['Edit4'].Texts()[0]
hthead[u'单耗申报环节']     = c['Edit1'].Texts()[0]
hthead[u'限制类标志']       = c['Edit2'].Texts()[0]
hthead[u'备注']             = c['Edit17'].Texts()[0]

l = c['ListView1']
htkouan=[]
for i in range(l.ItemCount()):
    kouan={}
    kouan[u'进出口岸序号']=l.GetItem(i,0)['text']
    kouan[u'进出口岸代码']=l.GetItem(i,1)['text']
    kouan[u'进出口岸名称']=l.GetItem(i,2)['text']
    htkouan.append(kouan)

dlg["TabControl"].TypeKeys("^{TAB}")

l = c['ListView2']
htliaojian=[]
l.Scroll('down','end')
for i in range(l.ItemCount()):
    liaojian={}
    liaojian[u'料件序号']=l.GetItem(i,0)['text']
    liaojian[u'商品编号']=l.GetItem(i,1)['text']
    liaojian[u'附加编号']=l.GetItem(i,2)['text']
    liaojian[u'商品名称']=l.GetItem(i,3)['text']
    liaojian[u'规格型号']=l.GetItem(i,4)['text']
    liaojian[u'申报计量单位']=l.GetItem(i,5)['text']
    liaojian[u'申报数量']=l.GetItem(i,6)['text']
    liaojian[u'申报单价']=l.GetItem(i,7)['text']
    liaojian[u'总价']=l.GetItem(i,8)['text']
    liaojian[u'处理标记']=l.GetItem(i,9)['text']
    liaojian[u'征税比例']=l.GetItem(i,10)['text']
    l.Select(i)
    liaojian[u'征免方式']=dlg['Edit13'].Texts()[0]
    liaojian[u'产销国']=dlg['Edit14'].Texts()[0]
    htliaojian.append(liaojian)

dlg["TabControl"].TypeKeys("^{TAB}")

l = c['ListView3']
htchenping=[]
l.Scroll('down','end')
for i in range(l.ItemCount()):
    chenping={}
    chenping[u'成品序号']=l.GetItem(i,0)['text']
    chenping[u'商品编号']=l.GetItem(i,1)['text']
    chenping[u'附加编号']=l.GetItem(i,2)['text']
    chenping[u'商品名称']=l.GetItem(i,3)['text']
    chenping[u'规格型号']=l.GetItem(i,4)['text']
    chenping[u'申报计量单位']=l.GetItem(i,5)['text']
    chenping[u'申报数量']=l.GetItem(i,6)['text']
    chenping[u'申报单价']=l.GetItem(i,7)['text']
    chenping[u'总价']=l.GetItem(i,8)['text']
    chenping[u'处理标记']=l.GetItem(i,9)['text']
    chenping[u'申报状态']=l.GetItem(i,10)['text']
    l.Select(i)
    chenping[u'征免方式']=dlg['Edit13'].Texts()[0]
    chenping[u'产销国']=dlg['Edit14'].Texts()[0]
    htchenping.append(chenping)

dlg["TabControl"].TypeKeys("^{TAB}")

l = c['ListView4']
htdanhao=[]
for i in range(l.ItemCount()):
    danhao={}
    danhao[u'成品序号']=l.GetItem(i,0)['text']
    danhao[u'成品名称']=l.GetItem(i,1)['text']
    danhao[u'料件序号']=l.GetItem(i,2)['text']
    danhao[u'料件名称']=l.GetItem(i,3)['text']
    danhao[u'单耗']=l.GetItem(i,4)['text']
    danhao[u'损耗率']=l.GetItem(i,5)['text']
    danhao[u'修改标记']=l.GetItem(i,6)['text']
    danhao[u'非保税料比例']=l.GetItem(i,7)['text']
    htdanhao.append(danhao)

dlg["TabControl"].TypeKeys("^{TAB}")
export={}
export[u'手册表头']=hthead
export[u'进出口岸']=htkouan
export[u'手册料件']=htliaojian
export[u'手册成品']=htchenping
export[u'手册单耗']=htdanhao

msg= json.dumps(export)
print msg
#f=open("tongguanbeian.txt","w")
#f.write(msg)
#f.close()

#print "-----head-----"
#for k,v in export['hthead'].items():
#    print k+":"+v

#print "-----kouan-----"
#for x in export['htkouan']:
#    print " | ".join([ k+":"+v for k,v in x.items()])

#print "-----liaojian-----"
#for x in export['htliaojian']:
#    print " | ".join([ k+":"+v for k,v in x.items()])

#print "-----chengping-----"
#for x in export['手册成品']:
#    print " | ".join([ k+":"+v for k,v in x.items()])

#print "-----danhao-----"
#for x in export['htdanhao']:
#    print " | ".join([ k+":"+v for k,v in x.items()])
