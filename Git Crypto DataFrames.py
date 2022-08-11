#!/usr/bin/env python
# coding: utf-8

# In[3]:


import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

get_ipython().run_line_magic('matplotlib', 'inline')


# In[25]:


bitcoin= pd.read_csv('btc-market-price.csv')


# In[26]:


bitcoin = pd.read_csv('btc-market-price.csv', header= None)
bitcoin.columns= ["timestamp", "price"]
bitcoin['timestamp'] = pd.to_datetime(bitcoin['timestamp'])
bitcoin.set_index('timestamp', inplace=True)
bitcoin.plot()


# In[27]:


eth = pd.read_csv('eth-price.csv', parse_dates=True, index_col=0)
#pd.to_datetime(eth['UnixTimeStamp']).head()
#pd.to_datetime(eth['Date(UTC)']).head()
price= pd.DataFrame(index= bitcoin.index)
price['Ether']= eth['Value']
price['Bitcoin']= bitcoin['price']
price.plot()


# In[42]:


price["Bit > Eth"]= price['Bitcoin'] > price['Ether']
price.drop("MV", axis= 1)


# In[46]:


price.loc['2017-11-01':'2018-01-01'].plot(figsize=(12, 6))


# In[ ]:




