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
#    else:
#        print ctrl.friendlyclassname, ctrlname

hthead={}
hthead[u'企业内部编号']     = c['Edit35'].Texts()[0]
hthead[u'手册类型']         = c['Edit4'].Texts()[0]
hthead[u'电子手册编号']     = c['Edit32'].Texts()[0]
hthead[u'收货地区']         = c['Edit16'].Texts()[0]
hthead[u'经营单位']         = c['Edit34'].Texts()[0]
hthead[u'经营单位名称']     = c['Edit33'].Texts()[0]
hthead[u'加工单位']         = c['Edit31'].Texts()[0]
hthead[u'加工单位名称']     = c['Edit30'].Texts()[0]
hthead[u'外商公司']         = c['Edit26'].Texts()[0]
hthead[u'外商经理人']       = c['Edit24'].Texts()[0]
hthead[u'贸易方式']         = c['Edit14'].Texts()[0]
hthead[u'征免性质']         = c['Edit15'].Texts()[0]
hthead[u'起抵地']           = c['Edit12'].Texts()[0]
hthead[u'成交方式']         = c['Edit13'].Texts()[0]
hthead[u'内销比']           = c['Edit9'].Texts()[0]
hthead[u'协议号']           = c['Edit23'].Texts()[0]
hthead[u'许可证号']         = c['Edit25'].Texts()[0]
hthead[u'批准文号']         = c['Edit29'].Texts()[0]
hthead[u'进口合同']         = c['Edit27'].Texts()[0]
hthead[u'出口合同']         = c['Edit22'].Texts()[0]
hthead[u'备案进口总额']     = c['Edit11'].Texts()[0]
hthead[u'进口币制']         = c['Edit28'].Texts()[0]
hthead[u'备案出口总额']     = c['Edit10'].Texts()[0]
hthead[u'出口币制']         = c['Edit17'].Texts()[0]
hthead[u'加工种类']         = c['Edit19'].Texts()[0]
hthead[u'保税方式']         = c['Edit18'].Texts()[0]
hthead[u'有效期限']         = c['Edit8'].Texts()[0]
hthead[u'进出口岸']         = c['Edit20'].Texts()[0]
hthead[u'主管海关']         = c['Edit7'].Texts()[0]
hthead[u'主管外经贸部门']   = c['Edit6'].Texts()[0]
hthead[u'处理标志']         = c['Edit5'].Texts()[0]
hthead[u'管理对象']         = c['Edit36'].Texts()[0]
hthead[u'录入日期']         = c['Edit2'].Texts()[0]
hthead[u'申报日期']         = c['Edit3'].Texts()[0]
hthead[u'限制类标志']       = c['Edit1'].Texts()[0]
hthead[u'备注']             = c['Edit21'].Texts()[0]

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
    liaojian[u'商品名称']=l.GetItem(i,2)['text']
    liaojian[u'规格型号']=l.GetItem(i,3)['text']
    liaojian[u'计量单位']=l.GetItem(i,4)['text']
    liaojian[u'单价']=l.GetItem(i,5)['text']
    liaojian[u'处理标记']=l.GetItem(i,6)['text']
    l.Select(i)
    liaojian[u'附加编码']=dlg['Edit7'].Texts()[0]
    htliaojian.append(liaojian)

dlg["TabControl"].TypeKeys("^{TAB}")
l = c['ListView3']
l.Scroll('down','end')
htchenping=[]
for i in range(l.ItemCount()):
    chenping={}
    chenping[u'成品序号']=l.GetItem(i,0)['text']
    chenping[u'商品编号']=l.GetItem(i,1)['text']
    chenping[u'商品名称']=l.GetItem(i,2)['text']
    chenping[u'规格型号']=l.GetItem(i,3)['text']
    chenping[u'计量单位']=l.GetItem(i,4)['text']
    chenping[u'单价']=l.GetItem(i,5)['text']
    chenping[u'处理标记']=l.GetItem(i,6)['text']
    l.Select(i)
    chenping[u'附加编码']=dlg['Edit10'].Texts()[0]
    htchenping.append(chenping)

dlg["TabControl"].TypeKeys("^{TAB}")
export={}
export[u'合同表头']=hthead
export[u'进出口岸']=htkouan
export[u'合同料件']=htliaojian
export[u'合同成品']=htchenping

msg= json.dumps(export)
print msg
#f=open("htongbeian.txt","w")
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
#for x in export['htchenping']:
#    print " | ".join([ k+":"+v for k,v in x.items()])