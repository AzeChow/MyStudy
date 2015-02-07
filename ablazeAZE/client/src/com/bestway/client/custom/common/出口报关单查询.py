# -*- coding: utf-8 -*-

from pywinauto import application
import pywinauto
import json
import time

app = application.Application().connect(title_re=u"中国电子口岸客户端.*")
dlg = app.window_(title_re = u'中国电子口岸客户端.*')

#dlg.ComboxBox2.Select(0) #进口报关单
if dlg.ComboxBox2.SelectedIndex()!=1:
    dlg.ComboxBox2.Select(1) #出口报关单
#if dlg.ComboxBox1.SelectedIndex()!=1:
#  dlg.ComboxBox1.Select(1) #设置选中经营单位

dlg[u'报关单号'].SetFocus()
dlg[u'报关单号'].TypeKeys('{ENTER}')
dlg['edit4'].SetText('ReplaceDateOrCustomsDeclarationCode')
dlg[u'开始查询(&A)'].SetFocus()
dlg[u'开始查询(&A)'].TypeKeys('{ENTER}')
dlg['ListView1'].Select(0)
dlg[u'查看明细(&C)'].SetFocus()
dlg[u'查看明细(&C)'].TypeKeys('{ENTER}')
ctrls = pywinauto.application._resolve_control(dlg.criteria)
dialog_controls = ctrls[-1].Children()
name_control_map = pywinauto.findbestmatch.build_unique_dict(dialog_controls)
ctrls={}
for ctrlname,c in name_control_map.items():
    if c.friendlyclassname=='Edit':
        ctrls[ctrlname]=c
bgdhead={}
bgdhead[u'备注'] = ctrls['Edit1'].Texts()[0]
bgdhead[u'报关单类型'] = ctrls['Edit2'].Texts()[0]
bgdhead[u'随附单证'] = ctrls['Edit3'].Texts()[0]
bgdhead[u'集装箱号'] = ctrls['Edit4'].Texts()[0]
bgdhead[u'净重'] = ctrls['Edit5'].Texts()[0]
bgdhead[u'毛重'] = ctrls['Edit6'].Texts()[0]
bgdhead[u'包装种类'] = ctrls['Edit7'].Texts()[0]
bgdhead[u'件数'] = ctrls['Edit8'].Texts()[0]
bgdhead[u'杂费3'] = ctrls['Edit9'].Texts()[0]
bgdhead[u'杂费2'] = ctrls['Edit10'].Texts()[0]
bgdhead[u'杂费1'] = ctrls['Edit11'].Texts()[0]
bgdhead[u'保费3'] = ctrls['Edit12'].Texts()[0]
bgdhead[u'保费2'] = ctrls['Edit13'].Texts()[0]
bgdhead[u'保费1'] = ctrls['Edit14'].Texts()[0]
bgdhead[u'运费3'] = ctrls['Edit15'].Texts()[0]
bgdhead[u'运费2'] = ctrls['Edit16'].Texts()[0]
bgdhead[u'运费1'] = ctrls['Edit17'].Texts()[0]
bgdhead[u'成交方式'] = ctrls['Edit18'].Texts()[0]
bgdhead[u'批准文号'] = ctrls['Edit19'].Texts()[0]
bgdhead[u'境内货源地'] = ctrls['Edit20'].Texts()[0]
bgdhead[u'指运港'] = ctrls['Edit21'].Texts()[0]
bgdhead[u'运抵国'] = ctrls['Edit22'].Texts()[0]
bgdhead[u'许可证号'] = ctrls['Edit23'].Texts()[0]
bgdhead[u'纳税单位'] = ctrls['Edit24'].Texts()[0]
bgdhead[u'结汇方式'] = ctrls['Edit25'].Texts()[0]
bgdhead[u'征免性质'] = ctrls['Edit26'].Texts()[0]
bgdhead[u'监管方式'] = ctrls['Edit27'].Texts()[0]
bgdhead[u'提运单号'] = ctrls['Edit28'].Texts()[0]
bgdhead[u'航次号'] = ctrls['Edit29'].Texts()[0]
bgdhead[u'申报单位2'] = ctrls['Edit30'].Texts()[0]
bgdhead[u'申报单位1'] = ctrls['Edit31'].Texts()[0]
bgdhead[u'运输工具名称'] = ctrls['Edit32'].Texts()[0]
bgdhead[u'发货单位2'] = ctrls['Edit33'].Texts()[0]
bgdhead[u'发货单位1'] = ctrls['Edit34'].Texts()[0]
bgdhead[u'经营单位编码'] = ctrls['Edit35'].Texts()[0]
bgdhead[u'运输方式'] = ctrls['Edit36'].Texts()[0]
bgdhead[u'经营单位3'] = ctrls['Edit37'].Texts()[0]
bgdhead[u'经营单位2'] = ctrls['Edit38'].Texts()[0]
bgdhead[u'申报日期'] = ctrls['Edit39'].Texts()[0]
bgdhead[u'出口日期'] = ctrls['Edit40'].Texts()[0]
bgdhead[u'合同协议号'] = ctrls['Edit41'].Texts()[0]
bgdhead[u'备案号'] = ctrls['Edit42'].Texts()[0]
bgdhead[u'出口口岸'] = ctrls['Edit43'].Texts()[0]
bgdhead[u'海关编号'] = ctrls['Edit44'].Texts()[0]
bgdhead[u'预录入编号'] = ctrls['Edit45'].Texts()[0]
bgdhead[u'统一编号'] = ctrls['Edit46'].Texts()[0]
bgdhead[u'货场代号'] = ctrls['Edit47'].Texts()[0]
bgdhead[u'监管仓号'] = ctrls['Edit48'].Texts()[0]
bgdhead[u'关联备案'] = ctrls['Edit49'].Texts()[0]
bgdhead[u'关联报关单'] = ctrls['Edit50'].Texts()[0]
bgdhead[u'联系方式'] = ctrls['Edit51'].Texts()[0]
bgdhead[u'报关员'] = ctrls['Edit52'].Texts()[0]
bgditems=[]

dlg.ListView3.TypeKeys("{DOWN}")
dlg.ListView3.TypeKeys("{UP}")
time.sleep(0.5)
bgditem={}
bgditem[u'商品编号'] = ctrls['Edit59'].Texts()[0]
bgditem[u'商品名称'] = ctrls['Edit60'].Texts()[0]
bgditem[u'用途'] = ctrls['Edit61'].Texts()[0]
bgditem[u'征免'] = ctrls['Edit62'].Texts()[0]#
bgditem[u'原产地'] = ctrls['Edit63'].Texts()[0]
bgditem[u'第二单位'] = ctrls['Edit64'].Texts()[0]
bgditem[u'第二数量'] = ctrls['Edit65'].Texts()[0]
bgditem[u'货号'] = ctrls['Edit66'].Texts()[0]
bgditem[u'版本号'] = ctrls['Edit67'].Texts()[0]
bgditem[u'法定单位'] = ctrls['Edit68'].Texts()[0]
bgditem[u'法定数量'] = ctrls['Edit69'].Texts()[0]
bgditem[u'币制'] = ctrls['Edit70'].Texts()[0]#
bgditem[u'成交总价'] = ctrls['Edit71'].Texts()[0]
bgditem[u'成交单价'] = ctrls['Edit72'].Texts()[0]
bgditem[u'成交单位'] = ctrls['Edit73'].Texts()[0]
bgditem[u'成交数量'] = ctrls['Edit74'].Texts()[0]
bgditem[u'规格型号'] = ctrls['Edit75'].Texts()[0]
bgditem[u'附加编号'] = ctrls['Edit76'].Texts()[0]
bgditem[u'备案序号'] = ctrls['Edit77'].Texts()[0]#
bgditem[u'商品序号'] = ctrls['Edit78'].Texts()[0]
bgditems.append(bgditem)
for i in range(dlg.ListView3.ItemCount()):       
    dlg.ListView3.TypeKeys("{DOWN}")
    time.sleep(0.5)
    bgditem={}
    bgditem[u'商品编号'] = ctrls['Edit58'].Texts()[0]
    bgditem[u'商品名称'] = ctrls['Edit59'].Texts()[0]
    bgditem[u'征免'] = ctrls['Edit60'].Texts()[0]
    bgditem[u'目的地'] = ctrls['Edit62'].Texts()[0]
    bgditem[u'工缴费'] = ctrls['Edit63'].Texts()[0]
    bgditem[u'第二单位'] = ctrls['Edit64'].Texts()[0]
    bgditem[u'第二数量'] = ctrls['Edit65'].Texts()[0]
    bgditem[u'生产厂家'] = ctrls['Edit66'].Texts()[0]
    bgditem[u'货号'] = ctrls['Edit67'].Texts()[0]
    bgditem[u'版本号'] = ctrls['Edit68'].Texts()[0]
    bgditem[u'法定单位'] = ctrls['Edit69'].Texts()[0]
    bgditem[u'法定数量'] = ctrls['Edit70'].Texts()[0]
    bgditem[u'币制'] = ctrls['Edit71'].Texts()[0]
    bgditem[u'成交总价'] = ctrls['Edit72'].Texts()[0]
    bgditem[u'成交单价'] = ctrls['Edit73'].Texts()[0]
    bgditem[u'成交单位'] = ctrls['Edit74'].Texts()[0]
    bgditem[u'成交数量'] = ctrls['Edit75'].Texts()[0]
    bgditem[u'规格型号'] = ctrls['Edit76'].Texts()[0]
    bgditem[u'附加编号'] = ctrls['Edit77'].Texts()[0]
    bgditem[u'备案序号'] = ctrls['Edit78'].Texts()[0]
    bgditem[u'商品序号'] = ctrls['Edit79'].Texts()[0]
    bgditems.append(bgditem)
sfdz=[]
for i in range(dlg.ListView1.ItemCount()):       
    sitem={}
    sitem[u'代码']=dlg.ListView1.GetItem(i,0)['text']
    sitem[u'随附单证编号']=dlg.ListView1.GetItem(i,1)['text']
    sfdz.append(sitem)
jzxh=[]
for i in range(dlg.ListView2.ItemCount()):       
    sitem={}
    sitem[u'集装箱号']=dlg.ListView2.GetItem(i,0)['text']
    sitem[u'规格']=dlg.ListView2.GetItem(i,1)['text']
    sitem[u'自重']=dlg.ListView2.GetItem(i,2)['text']
    jzxh.append(sitem)
bgdresult={}
bgdresult[u'报关单表头']=bgdhead
bgdresult[u'报关单表体']=bgditems
bgdresult[u'随附单证']=sfdz
bgdresult[u'集装箱号']=jzxh
dlg.Menu().Items()[10].SubMenu().Items()[2].Select()

msg = json.dumps(bgdresult)
print msg
time.sleep(1)
# f=open(u"D:\\Python\\新建文件夹\\jinkoubaoguandan.txt","w")
# f.write(msg)
# f.close()
