# -*- coding: utf-8 -*-

from pywinauto import application
import pywinauto
import json
import time

app = application.Application().connect(title_re=u"中国电子口岸客户端.*")
dlg = app.window_(title_re = u'中国电子口岸客户端.*')

if dlg.ComboxBox2.SelectedIndex()!=0:
    dlg.ComboxBox2.Select(0) #进口报关单
#dlg.ComboxBox2.Select(1) #出口报关单

dlg[u'上载日期'].SetFocus()
dlg[u'上载日期'].TypeKeys('{ENTER}')
#dlg.edit1.TypeKeys("20131210")  #查询日期
dlg['edit1'].SetText('ReplaceDateOrCustomsDeclarationCode')
dlg[u'开始查询(&A)'].SetFocus()
dlg[u'开始查询(&A)'].TypeKeys('{ENTER}')

while not dlg[u'开始查询(&A)'].IsEnabled():
    time.sleep(1)

bgds=[]
for i in range(dlg.ListView1.ItemCount()):
    bgds.append(dlg.ListView1.GetItem(i,0)['text'])
    
msg = json.dumps(bgds)
print msg
#f=open(u"D:\\Python\\新建文件夹\\danju.txt","w")
#f.write(msg)
#f.close()
