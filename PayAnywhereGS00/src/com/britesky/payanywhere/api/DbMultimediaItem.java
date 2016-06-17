// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.britesky.payanywhere.api;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import android.text.TextUtils;

@DatabaseTable (tableName = "multimedia_items")
final class DbMultimediaItem extends CoreDbHelper.HasPk
{

    static final String PATH_KEY = "_file_absolute_path";
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte _byte_array[];
    @DatabaseField
    private String _file_absolute_path;
    @DatabaseField
    private long _nab_pk;

    DbMultimediaItem()
    {
    }

    DbMultimediaItem(DbMultimediaItem dbmultimediaitem)
    {
        if (dbmultimediaitem == null)
        {
            _file_absolute_path = null;
            _byte_array = null;
            return;
        } else
        {
            _file_absolute_path = dbmultimediaitem._file_absolute_path;
            _byte_array = dbmultimediaitem._byte_array;
            return;
        }
    }

    DbMultimediaItem(String s)
    {
        _file_absolute_path = s;
    }

    DbMultimediaItem(byte abyte0[])
    {
        _byte_array = abyte0;
    }

    final String getFilePath()
    {
        return _file_absolute_path;
    }

    final long getNabPk()
    {
        return _nab_pk;
    }

    final byte[] getRawBytes()
    {
        return _byte_array;
    }

    final boolean setFilePath(String s)
    {
        if (TextUtils.equals(_file_absolute_path, s))
        {
            return false;
        } else
        {
            _file_absolute_path = s;
            return true;
        }
    }

    final void setNabPk(long l)
    {
        _nab_pk = l;
    }
}
