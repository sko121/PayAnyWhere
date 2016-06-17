package com.britesky.payanywhere.api;

import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.ForeignCollection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.nabancard.api:
//            DbFilter, Rule, DbRule

public class Filter
{

//    private eFilterType _filterType;
//    private boolean _matchAll;
//    private String _name;
//    private long _pk;
//    private ArrayList _rules;
//    ArrayList conflicts;
//    ArrayList warnings;
//
//    Filter(long l)
//    {
//        this(l, null, false, null, null);
//    }
//
//    private Filter(long l, String s, boolean flag, ArrayList arraylist, eFilterType efiltertype)
//    {
//        _matchAll = true;
//        _rules = new ArrayList();
//        warnings = new ArrayList();
//        conflicts = new ArrayList();
//        _pk = l;
//        _name = s;
//        _matchAll = flag;
//        if (arraylist == null)
//        {
//            arraylist = new ArrayList();
//        }
//        _rules = arraylist;
//        _filterType = efiltertype;
//    }
//
//    Filter(DbFilter dbfilter)
//    {
//        this(dbfilter.getPk(), dbfilter.getName(), dbfilter.getMatchAll(), populateRules(dbfilter), convertFilterType(dbfilter.getReportType()));
//    }
//
//    Filter(eFilterType efiltertype)
//    {
//        this(-1L, null, true, null, efiltertype);
//    }
//
//    public Filter(Filter filter)
//    {
//        this(filter._pk, filter._name, filter._matchAll, new ArrayList(filter._rules), filter._filterType);
//    }
//
//    private boolean compareRuleArrays(ArrayList arraylist, ArrayList arraylist1)
//    {
//        Iterator iterator;
//        if (arraylist == null && arraylist1 != null || arraylist != null && arraylist1 == null)
//        {
//            return false;
//        }
//        if (arraylist == null && arraylist1 == null)
//        {
//            return true;
//        }
//        iterator = arraylist.iterator();
//_L2:
//        Rule rule;
//        Iterator iterator1;
//        if (!iterator.hasNext())
//        {
//            break MISSING_BLOCK_LABEL_89;
//        }
//        rule = (Rule)iterator.next();
//        iterator1 = arraylist1.iterator();
//_L4:
//        if (!iterator1.hasNext()) goto _L2; else goto _L1
//_L1:
//        if (rule.compareTo((Rule)iterator1.next()) == 0) goto _L4; else goto _L3
//_L3:
//        return false;
//        return true;
//    }
//
//    static DbFilter.eFilterType convertFilterType(eFilterType efiltertype)
//    {
//        switch (_cls1..SwitchMap.com.nabancard.api.Filter.eFilterType[efiltertype.ordinal()])
//        {
//        default:
//            return null;
//
//        case 1: // '\001'
//            return DbFilter.eFilterType.ITEM;
//
//        case 2: // '\002'
//            return DbFilter.eFilterType.TRANSACTION;
//        }
//    }
//
//    static eFilterType convertFilterType(DbFilter.eFilterType efiltertype)
//    {
//        switch (_cls1..SwitchMap.com.nabancard.api.DbFilter.eFilterType[efiltertype.ordinal()])
//        {
//        default:
//            return null;
//
//        case 1: // '\001'
//            return eFilterType.ITEM;
//
//        case 2: // '\002'
//            return eFilterType.TRANSACTION;
//        }
//    }
//
//    private float getGenericFloat(String s)
//    {
//        if (s.startsWith("$"))
//        {
//            return Float.parseFloat(s.substring(1));
//        } else
//        {
//            return Float.parseFloat(s);
//        }
//    }
//
//    private static ArrayList populateRules(DbFilter dbfilter)
//    {
//        ArrayList arraylist;
//        CloseableWrappedIterable closeablewrappediterable;
//        arraylist = new ArrayList();
//        closeablewrappediterable = dbfilter.getRules().getWrappedIterable();
//        for (Iterator iterator = closeablewrappediterable.iterator(); iterator.hasNext(); arraylist.add(new Rule((DbRule)iterator.next()))) { }
//        break MISSING_BLOCK_LABEL_70;
//        Exception exception;
//        exception;
//        SQLException sqlexception1;
//        try
//        {
//            closeablewrappediterable.close();
//        }
//        catch (SQLException sqlexception)
//        {
//            sqlexception.printStackTrace();
//        }
//        throw exception;
//        try
//        {
//            closeablewrappediterable.close();
//        }
//        // Misplaced declaration of an exception variable
//        catch (SQLException sqlexception1)
//        {
//            sqlexception1.printStackTrace();
//            return arraylist;
//        }
//        return arraylist;
//    }
//
//    private boolean setConflict(Rule rule, Rule rule1, eConflictType econflicttype)
//    {
//        return setConflictWarning(rule, rule1, econflicttype, conflicts, Rule.eWarningState.CONFLICT);
//    }
//
//    private boolean setConflictWarning(Rule rule, Rule rule1, eConflictType econflicttype, ArrayList arraylist, Rule.eWarningState ewarningstate)
//    {
//        Iterator iterator = arraylist.iterator();
//        boolean flag = false;
//        while (iterator.hasNext()) 
//        {
//            Conflict conflict = (Conflict)iterator.next();
//            boolean flag1;
//            if (conflict.getConflictType() == econflicttype)
//            {
//                conflict.addPair(rule, rule1);
//                flag1 = true;
//            } else
//            {
//                flag1 = flag;
//            }
//            flag = flag1;
//        }
//        if (!flag)
//        {
//            arraylist.add((new Conflict(econflicttype, null)).addPair(rule, rule1));
//        }
//        rule.setWarningState(ewarningstate);
//        rule1.setWarningState(ewarningstate);
//        return false;
//    }
//
//    private boolean setWarning(Rule rule, Rule rule1, eConflictType econflicttype)
//    {
//        return setConflictWarning(rule, rule1, econflicttype, warnings, Rule.eWarningState.WARNING);
//    }
//
//    public ArrayList getConflicts()
//    {
//        return conflicts;
//    }
//
//    public eFilterType getFilterType()
//    {
//        return _filterType;
//    }
//
//    public boolean getMatchAll()
//    {
//        return _matchAll;
//    }
//
//    public String getName()
//    {
//        return _name;
//    }
//
//    long getPk()
//    {
//        return _pk;
//    }
//
//    public ArrayList getRules()
//    {
//        return _rules;
//    }
//
//    public ArrayList getWarnings()
//    {
//        return warnings;
//    }
//
//    public boolean isEqualTo(Filter filter)
//    {
//        return _pk == filter._pk && _name.equals(filter._name) && _matchAll == filter._matchAll && compareRuleArrays(_rules, filter._rules) && _filterType == filter._filterType;
//    }
//
//    public void setMatchAll(boolean flag)
//    {
//        _matchAll = flag;
//    }
//
//    public void setName(String s)
//    {
//        _name = s;
//    }
//
//    void setPk(long l)
//    {
//        _pk = l;
//    }
//
//    public boolean updateWarningLevels(boolean flag)
//    {
//        int i;
//        boolean flag2;
//        boolean flag1;
//        if (!flag)
//        {
//            flag1 = true;
//        } else
//        {
//            flag1 = false;
//        }
//        _matchAll = flag1;
//        for (Iterator iterator = _rules.iterator(); iterator.hasNext(); ((Rule)iterator.next()).resetWarningState()) { }
//        warnings.clear();
//        conflicts.clear();
//        if (flag)
//        {
//            return true;
//        }
//        i = 0;
//        flag2 = true;
//_L18:
//        Rule rule;
//        int j;
//        if (i >= _rules.size())
//        {
//            break; /* Loop/switch isn't completed */
//        }
//        rule = (Rule)_rules.get(i);
//        j = 0;
//_L8:
//        if (j >= _rules.size()) goto _L2; else goto _L1
//_L1:
//        if (i == j) goto _L4; else goto _L3
//_L3:
//        Rule rule1 = (Rule)_rules.get(j);
//        if (rule.getTransactionType() != Rule.eTransactionType.ALL_TRANSACTIONS && rule1.getTransactionType() != Rule.eTransactionType.ALL_TRANSACTIONS && rule.getTransactionType() != rule1.getTransactionType() || rule.getAttribute() != rule1.getAttribute()) goto _L4; else goto _L5
//_L5:
//        Rule.eDataType edatatype;
//        Rule.eOperator eoperator;
//        Rule.eOperator eoperator1;
//        edatatype = rule.getType();
//        eoperator = rule.getOperator();
//        eoperator1 = rule1.getOperator();
//        if (edatatype != Rule.eDataType.BOOL && edatatype != Rule.eDataType.CARD_INPUT && edatatype != Rule.eDataType.CARD_PROCESS && edatatype != Rule.eDataType.IMAGE && edatatype != Rule.eDataType.CARD) goto _L7; else goto _L6
//_L6:
//        if (rule.getOperator() == rule1.getOperator())
//        {
//            flag2 = setWarning(rule, rule1, eConflictType.SINGLE_CHOICE_LIST_REDUNDANCY);
//        } else
//        {
//            flag2 = setConflict(rule, rule1, eConflictType.SINGLE_CHOICE_LIST_CONFLICTING);
//        }
//_L4:
//        j++;
//          goto _L8
//_L7:
//        float f;
//        float f1;
//        if (edatatype != Rule.eDataType.NUMBER)
//        {
//            break MISSING_BLOCK_LABEL_1083;
//        }
//        f = getGenericFloat(rule.getValue());
//        f1 = getGenericFloat(rule1.getValue());
//        if (eoperator != eoperator1)
//        {
//            break MISSING_BLOCK_LABEL_435;
//        }
//        _cls1..SwitchMap.com.nabancard.api.Rule.eOperator[eoperator.ordinal()];
//        JVM INSTR tableswitch 1 6: default 376
//    //                   1 387
//    //                   2 411
//    //                   3 419
//    //                   4 419
//    //                   5 419
//    //                   6 419;
//           goto _L9 _L10 _L11 _L12 _L12 _L12 _L12
//_L9:
//        boolean flag3 = flag2;
//_L14:
//        flag2 = flag3;
//          goto _L4
//_L10:
//        if (f == f1) goto _L12; else goto _L13
//_L13:
//        flag2 = setConflict(rule, rule1, eConflictType.NUMBER_MULTIPLE_EQUALITY_CONFLICT);
//          goto _L4
//_L11:
//        if (f != f1) goto _L9; else goto _L12
//_L12:
//        flag3 = setWarning(rule, rule1, eConflictType.NUMBER_IDENTICAL_RULES_REDUNDANCY);
//          goto _L14
//        if (eoperator == Rule.eOperator.LESS_THAN && eoperator1 == Rule.eOperator.LESS_THAN_OR_EQUAL)
//        {
//            flag2 = setWarning(rule, rule1, eConflictType.NUMBER_ONE_RANGE_CONTAINS_ANOTHER_REDUNDANCY);
//        } else
//        if (eoperator == Rule.eOperator.LESS_THAN && eoperator1 == Rule.eOperator.EQUAL)
//        {
//            if (f1 < f)
//            {
//                flag2 = setWarning(rule, rule1, eConflictType.NUMBER_WILL_ONLY_SHOW_EQUALITY_REDUNDANCY);
//            } else
//            {
//                flag2 = setConflict(rule, rule1, eConflictType.NUMBER_EQUALITY_OUT_OF_RANGE_CONFLICTING);
//            }
//        } else
//        if (eoperator == Rule.eOperator.LESS_THAN && eoperator1 == Rule.eOperator.DOES_NOT_EQUAL)
//        {
//            if (f1 >= f)
//            {
//                flag2 = setWarning(rule, rule1, eConflictType.NUMBER_DNE_ALREADY_EXCLUDED_BY_RANGE_REDUNDANCY);
//            }
//        } else
//        if (eoperator == Rule.eOperator.LESS_THAN_OR_EQUAL && eoperator1 == Rule.eOperator.EQUAL)
//        {
//            if (f1 <= f)
//            {
//                flag2 = setWarning(rule, rule1, eConflictType.NUMBER_WILL_ONLY_SHOW_EQUALITY_REDUNDANCY);
//            } else
//            {
//                flag2 = setConflict(rule, rule1, eConflictType.NUMBER_EQUALITY_OUT_OF_RANGE_CONFLICTING);
//            }
//        } else
//        if (eoperator == Rule.eOperator.LESS_THAN_OR_EQUAL && eoperator1 == Rule.eOperator.DOES_NOT_EQUAL)
//        {
//            if (f1 > f)
//            {
//                flag2 = setWarning(rule, rule1, eConflictType.NUMBER_DNE_ALREADY_EXCLUDED_BY_RANGE_REDUNDANCY);
//            }
//        } else
//        if (eoperator == Rule.eOperator.GREATER_THAN && eoperator1 == Rule.eOperator.GREATER_THAN_OR_EQUAL)
//        {
//            flag2 = setWarning(rule, rule1, eConflictType.NUMBER_ONE_RANGE_CONTAINS_ANOTHER_REDUNDANCY);
//        } else
//        if (eoperator == Rule.eOperator.GREATER_THAN && eoperator1 == Rule.eOperator.EQUAL)
//        {
//            if (f1 > f)
//            {
//                flag2 = setWarning(rule, rule1, eConflictType.NUMBER_WILL_ONLY_SHOW_EQUALITY_REDUNDANCY);
//            } else
//            {
//                flag2 = setConflict(rule, rule1, eConflictType.NUMBER_EQUALITY_OUT_OF_RANGE_CONFLICTING);
//            }
//        } else
//        if (eoperator == Rule.eOperator.GREATER_THAN && eoperator1 == Rule.eOperator.DOES_NOT_EQUAL)
//        {
//            if (f1 <= f)
//            {
//                flag2 = setWarning(rule, rule1, eConflictType.NUMBER_DNE_ALREADY_EXCLUDED_BY_RANGE_REDUNDANCY);
//            }
//        } else
//        if (eoperator == Rule.eOperator.GREATER_THAN_OR_EQUAL && eoperator1 == Rule.eOperator.EQUAL)
//        {
//            if (f1 >= f)
//            {
//                flag2 = setWarning(rule, rule1, eConflictType.NUMBER_WILL_ONLY_SHOW_EQUALITY_REDUNDANCY);
//            } else
//            {
//                flag2 = setConflict(rule, rule1, eConflictType.NUMBER_EQUALITY_OUT_OF_RANGE_CONFLICTING);
//            }
//        } else
//        if (eoperator == Rule.eOperator.GREATER_THAN_OR_EQUAL && eoperator1 == Rule.eOperator.DOES_NOT_EQUAL)
//        {
//            if (f1 < f)
//            {
//                flag2 = setWarning(rule, rule1, eConflictType.NUMBER_DNE_ALREADY_EXCLUDED_BY_RANGE_REDUNDANCY);
//            }
//        } else
//        if (eoperator == Rule.eOperator.EQUAL && eoperator1 == Rule.eOperator.DOES_NOT_EQUAL)
//        {
//            if (f == f1)
//            {
//                flag2 = setConflict(rule, rule1, eConflictType.NUMBER_CANNOT_EQUAL_AND_NOT_EQUAL_CONFLICTING);
//            }
//        } else
//        if (eoperator == Rule.eOperator.LESS_THAN && eoperator1 == Rule.eOperator.GREATER_THAN_OR_EQUAL)
//        {
//            if (f < f1)
//            {
//                flag2 = setConflict(rule, rule1, eConflictType.NUMBER_RANGES_WILL_NEVER_MEET_CONFLICTING);
//            }
//        } else
//        if (eoperator == Rule.eOperator.LESS_THAN_OR_EQUAL && eoperator1 == Rule.eOperator.GREATER_THAN_OR_EQUAL)
//        {
//            if (f < f1)
//            {
//                flag2 = setConflict(rule, rule1, eConflictType.NUMBER_RANGES_WILL_NEVER_MEET_CONFLICTING);
//            }
//        } else
//        if (eoperator == Rule.eOperator.LESS_THAN && eoperator1 == Rule.eOperator.GREATER_THAN)
//        {
//            if (f <= f1)
//            {
//                flag2 = setConflict(rule, rule1, eConflictType.NUMBER_RANGES_WILL_NEVER_MEET_CONFLICTING);
//            }
//        } else
//        if (eoperator == Rule.eOperator.LESS_THAN_OR_EQUAL && eoperator1 == Rule.eOperator.GREATER_THAN && f < f1)
//        {
//            flag2 = setConflict(rule, rule1, eConflictType.NUMBER_RANGES_WILL_NEVER_MEET_CONFLICTING);
//        }
//          goto _L4
//        if (edatatype != Rule.eDataType.STRING && edatatype != Rule.eDataType.MATCH_STRING)
//        {
//            continue; /* Loop/switch isn't completed */
//        }
//        String s = rule.getValue();
//        String s1 = rule1.getValue();
//        if (eoperator == eoperator1)
//        {
//            switch (_cls1..SwitchMap.com.nabancard.api.Rule.eOperator[eoperator.ordinal()])
//            {
//            case 1: // '\001'
//                if (s.equals(s1))
//                {
//                    flag2 = setWarning(rule, rule1, eConflictType.STRING_EQUALS_REDUNDANCY);
//                } else
//                {
//                    flag2 = setConflict(rule, rule1, eConflictType.STRING_EQUALS_CONFLICTING);
//                }
//                break;
//
//            case 7: // '\007'
//                if (s.equals(s1) || s.startsWith(s1) || s1.startsWith(s))
//                {
//                    flag2 = setWarning(rule, rule1, eConflictType.STRING_BEGINS_WITH_REDUNDANCY);
//                } else
//                {
//                    flag2 = setConflict(rule, rule1, eConflictType.STRING_BEGINS_WITH_CONFLICTING);
//                }
//                break;
//
//            case 8: // '\b'
//                if (s.equals(s1) || s.endsWith(s1) || s1.endsWith(s))
//                {
//                    flag2 = setWarning(rule, rule1, eConflictType.STRING_ENDS_WITH_REDUNDANCY);
//                } else
//                {
//                    flag2 = setConflict(rule, rule1, eConflictType.STRING_ENDS_WITH_CONFLICTING);
//                }
//                break;
//
//            case 9: // '\t'
//                if (s.contains(s1) || s1.contains(s))
//                {
//                    flag2 = setWarning(rule, rule1, eConflictType.STRING_CONTAINS_REDUNDANCY);
//                }
//                break;
//            }
//        } else
//        if (eoperator == Rule.eOperator.CONTAINS && eoperator1 == Rule.eOperator.DOES_NOT_CONTAIN && (s.equals(s1) || s.contains(s1)))
//        {
//            flag2 = setConflict(rule, rule1, eConflictType.STRING_CONTAINS_AND_DOES_NOT_CONTAIN_CONFLICTING);
//        }
//        continue; /* Loop/switch isn't completed */
//        if (edatatype != Rule.eDataType.DATE) goto _L4; else goto _L15
//_L15:
//        long l = Long.parseLong(rule.getValue());
//        long l1 = Long.parseLong(rule1.getValue());
//        if (eoperator == eoperator1)
//        {
//            switch (_cls1..SwitchMap.com.nabancard.api.Rule.eOperator[eoperator.ordinal()])
//            {
//            case 10: // '\n'
//                if (l == l1)
//                {
//                    flag2 = setWarning(rule, rule1, eConflictType.DATE_ON_DATE_REDUNDANCY);
//                } else
//                {
//                    flag2 = setConflict(rule, rule1, eConflictType.DATE_ON_DATE_CONFLICTING);
//                }
//                break;
//
//            case 11: // '\013'
//                flag2 = setWarning(rule, rule1, eConflictType.DATE_ON_OR_AFTER_REDUNDANCY);
//                break;
//
//            case 12: // '\f'
//                flag2 = setWarning(rule, rule1, eConflictType.DATE_ON_OR_BEFORE_REDUNDANCY);
//                break;
//            }
//        } else
//        if (eoperator == Rule.eOperator.ON_OR_BEFORE && eoperator1 == Rule.eOperator.ON_DATE)
//        {
//            if (l1 <= l)
//            {
//                flag2 = setWarning(rule, rule1, eConflictType.DATE_RESULTS_WILL_BE_ONLY_ONE_DAY_REDUNDANCY);
//            } else
//            {
//                flag2 = setConflict(rule, rule1, eConflictType.DATE_RANGES_WILL_NEVER_CROSS_CONFLICTING);
//            }
//        } else
//        if (eoperator == Rule.eOperator.ON_OR_AFTER && eoperator1 == Rule.eOperator.ON_DATE)
//        {
//            if (l1 >= l)
//            {
//                flag2 = setWarning(rule, rule1, eConflictType.DATE_RESULTS_WILL_BE_ONLY_ONE_DAY_REDUNDANCY);
//            } else
//            {
//                flag2 = setConflict(rule, rule1, eConflictType.DATE_RANGES_WILL_NEVER_CROSS_CONFLICTING);
//            }
//        } else
//        if (eoperator == Rule.eOperator.ON_OR_BEFORE && eoperator1 == Rule.eOperator.ON_OR_AFTER)
//        {
//            if (l == l1)
//            {
//                flag2 = setWarning(rule, rule1, eConflictType.DATE_RESULTS_WILL_BE_ONLY_ONE_DAY_REDUNDANCY);
//            } else
//            if (l < l1)
//            {
//                flag2 = setConflict(rule, rule1, eConflictType.DATE_RANGES_WILL_NEVER_CROSS_CONFLICTING);
//            }
//        }
//        if (false)
//        {
//        }
//        continue; /* Loop/switch isn't completed */
//_L2:
//        i++;
//        continue; /* Loop/switch isn't completed */
//        if (true) goto _L4; else goto _L16
//_L16:
//        if (true) goto _L18; else goto _L17
//_L17:
//        return flag2;
//    }
//
//    private class _cls1
//    {
//
//        static final int $SwitchMap$com$nabancard$api$DbFilter$eFilterType[];
//        static final int $SwitchMap$com$nabancard$api$Filter$eFilterType[];
//        static final int $SwitchMap$com$nabancard$api$Rule$eOperator[];
//        static final int $SwitchMap$com$nabancard$api$Rule$eTransactionType[];
//
//        static 
//        {
//            $SwitchMap$com$nabancard$api$Rule$eOperator = new int[Rule.eOperator.values().length];
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.EQUAL.ordinal()] = 1;
//            }
//            catch (NoSuchFieldError nosuchfielderror) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.DOES_NOT_EQUAL.ordinal()] = 2;
//            }
//            catch (NoSuchFieldError nosuchfielderror1) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.GREATER_THAN.ordinal()] = 3;
//            }
//            catch (NoSuchFieldError nosuchfielderror2) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.GREATER_THAN_OR_EQUAL.ordinal()] = 4;
//            }
//            catch (NoSuchFieldError nosuchfielderror3) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.LESS_THAN.ordinal()] = 5;
//            }
//            catch (NoSuchFieldError nosuchfielderror4) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.LESS_THAN_OR_EQUAL.ordinal()] = 6;
//            }
//            catch (NoSuchFieldError nosuchfielderror5) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.BEGINS_WITH.ordinal()] = 7;
//            }
//            catch (NoSuchFieldError nosuchfielderror6) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.ENDS_WITH.ordinal()] = 8;
//            }
//            catch (NoSuchFieldError nosuchfielderror7) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.CONTAINS.ordinal()] = 9;
//            }
//            catch (NoSuchFieldError nosuchfielderror8) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.ON_DATE.ordinal()] = 10;
//            }
//            catch (NoSuchFieldError nosuchfielderror9) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.ON_OR_AFTER.ordinal()] = 11;
//            }
//            catch (NoSuchFieldError nosuchfielderror10) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eOperator[Rule.eOperator.ON_OR_BEFORE.ordinal()] = 12;
//            }
//            catch (NoSuchFieldError nosuchfielderror11) { }
//            $SwitchMap$com$nabancard$api$DbFilter$eFilterType = new int[DbFilter.eFilterType.values().length];
//            try
//            {
//                $SwitchMap$com$nabancard$api$DbFilter$eFilterType[DbFilter.eFilterType.ITEM.ordinal()] = 1;
//            }
//            catch (NoSuchFieldError nosuchfielderror12) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$DbFilter$eFilterType[DbFilter.eFilterType.TRANSACTION.ordinal()] = 2;
//            }
//            catch (NoSuchFieldError nosuchfielderror13) { }
//            $SwitchMap$com$nabancard$api$Filter$eFilterType = new int[eFilterType.values().length];
//            try
//            {
//                $SwitchMap$com$nabancard$api$Filter$eFilterType[eFilterType.ITEM.ordinal()] = 1;
//            }
//            catch (NoSuchFieldError nosuchfielderror14) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Filter$eFilterType[eFilterType.TRANSACTION.ordinal()] = 2;
//            }
//            catch (NoSuchFieldError nosuchfielderror15) { }
//            $SwitchMap$com$nabancard$api$Rule$eTransactionType = new int[Rule.eTransactionType.values().length];
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eTransactionType[Rule.eTransactionType.ALL_TRANSACTIONS.ordinal()] = 1;
//            }
//            catch (NoSuchFieldError nosuchfielderror16) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eTransactionType[Rule.eTransactionType.CASH.ordinal()] = 2;
//            }
//            catch (NoSuchFieldError nosuchfielderror17) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eTransactionType[Rule.eTransactionType.CASH_REFUND.ordinal()] = 3;
//            }
//            catch (NoSuchFieldError nosuchfielderror18) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eTransactionType[Rule.eTransactionType.CREDIT_CARD.ordinal()] = 4;
//            }
//            catch (NoSuchFieldError nosuchfielderror19) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eTransactionType[Rule.eTransactionType.CREDIT_CARD_REFUND.ordinal()] = 5;
//            }
//            catch (NoSuchFieldError nosuchfielderror20) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eTransactionType[Rule.eTransactionType.PRE_AUTHORIZATION.ordinal()] = 6;
//            }
//            catch (NoSuchFieldError nosuchfielderror21) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eTransactionType[Rule.eTransactionType.TIP.ordinal()] = 7;
//            }
//            catch (NoSuchFieldError nosuchfielderror22) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eTransactionType[Rule.eTransactionType.VOID.ordinal()] = 8;
//            }
//            catch (NoSuchFieldError nosuchfielderror23) { }
//            try
//            {
//                $SwitchMap$com$nabancard$api$Rule$eTransactionType[Rule.eTransactionType.INVENTORY.ordinal()] = 9;
//            }
//            catch (NoSuchFieldError nosuchfielderror24)
//            {
//                return;
//            }
//        }
//    }
//
//
//    private class eFilterType extends Enum
//    {
//
//        private static final eFilterType $VALUES[];
//        public static final eFilterType ITEM;
//        public static final eFilterType TRANSACTION;
//        private int _type;
//
//        public static eFilterType valueOf(String s)
//        {
//            return (eFilterType)Enum.valueOf(com/nabancard/api/Filter$eFilterType, s);
//        }
//
//        public static eFilterType[] values()
//        {
//            return (eFilterType[])$VALUES.clone();
//        }
//
//        public final boolean checkRuleCompatibility(Rule.eTransactionType etransactiontype)
//        {
//            boolean flag = true;
//            _cls1..SwitchMap.com.nabancard.api.Rule.eTransactionType[etransactiontype.ordinal()];
//            JVM INSTR tableswitch 1 9: default 60
//        //                       1 64
//        //                       2 64
//        //                       3 64
//        //                       4 64
//        //                       5 64
//        //                       6 64
//        //                       7 64
//        //                       8 64
//        //                       9 74;
//               goto _L1 _L2 _L2 _L2 _L2 _L2 _L2 _L2 _L2 _L3
//_L1:
//            flag = false;
//_L5:
//            return flag;
//_L2:
//            if (flag != _type)
//            {
//                return false;
//            }
//            continue; /* Loop/switch isn't completed */
//_L3:
//            if (_type != 0)
//            {
//                return false;
//            }
//            if (true) goto _L5; else goto _L4
//_L4:
//        }
//
//        public final int getFilterType()
//        {
//            return _type;
//        }
//
//        static 
//        {
//            ITEM = new eFilterType("ITEM", 0, 0);
//            TRANSACTION = new eFilterType("TRANSACTION", 1, 1);
//            eFilterType aefiltertype[] = new eFilterType[2];
//            aefiltertype[0] = ITEM;
//            aefiltertype[1] = TRANSACTION;
//            $VALUES = aefiltertype;
//        }
//
//        private eFilterType(String s, int i, int j)
//        {
//            super(s, i);
//            _type = j;
//        }
//    }
//
//
//    private class Conflict
//    {
//
//        private final eConflictType conflict;
//        private final ArrayList conflictingRules;
//        final Filter this$0;
//
//        private Conflict addPair(Rule rule, Rule rule1)
//        {
//            if (!conflictingRules.contains(rule))
//            {
//                conflictingRules.add(rule);
//            }
//            if (!conflictingRules.contains(rule1))
//            {
//                conflictingRules.add(rule1);
//            }
//            return this;
//        }
//
//        public eConflictType getConflictType()
//        {
//            return conflict;
//        }
//
//        public ArrayList getConflictingRules()
//        {
//            return conflictingRules;
//        }
//
//
//        private Conflict(eConflictType econflicttype)
//        {
//            this$0 = Filter.this;
//            super();
//            conflictingRules = new ArrayList();
//            conflict = econflicttype;
//        }
//
//        Conflict(eConflictType econflicttype, _cls1 _pcls1)
//        {
//            this(econflicttype);
//        }
//    }
//
//
//    private class eConflictType extends Enum
//    {
//
//        private static final eConflictType $VALUES[];
//        public static final eConflictType DATE_ON_DATE_CONFLICTING;
//        public static final eConflictType DATE_ON_DATE_REDUNDANCY;
//        public static final eConflictType DATE_ON_OR_AFTER_REDUNDANCY;
//        public static final eConflictType DATE_ON_OR_BEFORE_REDUNDANCY;
//        public static final eConflictType DATE_RANGES_WILL_NEVER_CROSS_CONFLICTING;
//        public static final eConflictType DATE_RESULTS_WILL_BE_ONLY_ONE_DAY_REDUNDANCY;
//        public static final eConflictType NUMBER_CANNOT_EQUAL_AND_NOT_EQUAL_CONFLICTING;
//        public static final eConflictType NUMBER_DNE_ALREADY_EXCLUDED_BY_RANGE_REDUNDANCY;
//        public static final eConflictType NUMBER_EQUALITY_OUT_OF_RANGE_CONFLICTING;
//        public static final eConflictType NUMBER_IDENTICAL_RULES_REDUNDANCY;
//        public static final eConflictType NUMBER_MULTIPLE_EQUALITY_CONFLICT;
//        public static final eConflictType NUMBER_ONE_RANGE_CONTAINS_ANOTHER_REDUNDANCY;
//        public static final eConflictType NUMBER_RANGES_WILL_NEVER_MEET_CONFLICTING;
//        public static final eConflictType NUMBER_WILL_ONLY_SHOW_EQUALITY_REDUNDANCY;
//        public static final eConflictType SINGLE_CHOICE_LIST_CONFLICTING;
//        public static final eConflictType SINGLE_CHOICE_LIST_REDUNDANCY;
//        public static final eConflictType STRING_BEGINS_WITH_CONFLICTING;
//        public static final eConflictType STRING_BEGINS_WITH_REDUNDANCY;
//        public static final eConflictType STRING_CONTAINS_AND_DOES_NOT_CONTAIN_CONFLICTING;
//        public static final eConflictType STRING_CONTAINS_REDUNDANCY;
//        public static final eConflictType STRING_ENDS_WITH_CONFLICTING;
//        public static final eConflictType STRING_ENDS_WITH_REDUNDANCY;
//        public static final eConflictType STRING_EQUALS_CONFLICTING;
//        public static final eConflictType STRING_EQUALS_REDUNDANCY;
//        private int conflictIndex;
//        private boolean isConflict;
//
//        public static eConflictType valueOf(String s)
//        {
//            return (eConflictType)Enum.valueOf(com/nabancard/api/Filter$eConflictType, s);
//        }
//
//        public static eConflictType[] values()
//        {
//            return (eConflictType[])$VALUES.clone();
//        }
//
//        public final int getConflictIndex()
//        {
//            return conflictIndex;
//        }
//
//        public final boolean isConflict()
//        {
//            return isConflict;
//        }
//
//        static 
//        {
//            SINGLE_CHOICE_LIST_REDUNDANCY = new eConflictType("SINGLE_CHOICE_LIST_REDUNDANCY", 0, 0, false);
//            SINGLE_CHOICE_LIST_CONFLICTING = new eConflictType("SINGLE_CHOICE_LIST_CONFLICTING", 1, 1, true);
//            STRING_BEGINS_WITH_REDUNDANCY = new eConflictType("STRING_BEGINS_WITH_REDUNDANCY", 2, 2, false);
//            STRING_BEGINS_WITH_CONFLICTING = new eConflictType("STRING_BEGINS_WITH_CONFLICTING", 3, 3, true);
//            STRING_ENDS_WITH_REDUNDANCY = new eConflictType("STRING_ENDS_WITH_REDUNDANCY", 4, 4, false);
//            STRING_ENDS_WITH_CONFLICTING = new eConflictType("STRING_ENDS_WITH_CONFLICTING", 5, 5, true);
//            STRING_EQUALS_REDUNDANCY = new eConflictType("STRING_EQUALS_REDUNDANCY", 6, 6, false);
//            STRING_EQUALS_CONFLICTING = new eConflictType("STRING_EQUALS_CONFLICTING", 7, 7, true);
//            STRING_CONTAINS_REDUNDANCY = new eConflictType("STRING_CONTAINS_REDUNDANCY", 8, 8, false);
//            STRING_CONTAINS_AND_DOES_NOT_CONTAIN_CONFLICTING = new eConflictType("STRING_CONTAINS_AND_DOES_NOT_CONTAIN_CONFLICTING", 9, 9, true);
//            DATE_ON_DATE_REDUNDANCY = new eConflictType("DATE_ON_DATE_REDUNDANCY", 10, 10, false);
//            DATE_ON_DATE_CONFLICTING = new eConflictType("DATE_ON_DATE_CONFLICTING", 11, 11, true);
//            DATE_ON_OR_AFTER_REDUNDANCY = new eConflictType("DATE_ON_OR_AFTER_REDUNDANCY", 12, 12, false);
//            DATE_ON_OR_BEFORE_REDUNDANCY = new eConflictType("DATE_ON_OR_BEFORE_REDUNDANCY", 13, 13, false);
//            DATE_RANGES_WILL_NEVER_CROSS_CONFLICTING = new eConflictType("DATE_RANGES_WILL_NEVER_CROSS_CONFLICTING", 14, 14, true);
//            DATE_RESULTS_WILL_BE_ONLY_ONE_DAY_REDUNDANCY = new eConflictType("DATE_RESULTS_WILL_BE_ONLY_ONE_DAY_REDUNDANCY", 15, 15, false);
//            NUMBER_IDENTICAL_RULES_REDUNDANCY = new eConflictType("NUMBER_IDENTICAL_RULES_REDUNDANCY", 16, 16, false);
//            NUMBER_MULTIPLE_EQUALITY_CONFLICT = new eConflictType("NUMBER_MULTIPLE_EQUALITY_CONFLICT", 17, 17, true);
//            NUMBER_ONE_RANGE_CONTAINS_ANOTHER_REDUNDANCY = new eConflictType("NUMBER_ONE_RANGE_CONTAINS_ANOTHER_REDUNDANCY", 18, 18, false);
//            NUMBER_EQUALITY_OUT_OF_RANGE_CONFLICTING = new eConflictType("NUMBER_EQUALITY_OUT_OF_RANGE_CONFLICTING", 19, 19, true);
//            NUMBER_WILL_ONLY_SHOW_EQUALITY_REDUNDANCY = new eConflictType("NUMBER_WILL_ONLY_SHOW_EQUALITY_REDUNDANCY", 20, 20, false);
//            NUMBER_DNE_ALREADY_EXCLUDED_BY_RANGE_REDUNDANCY = new eConflictType("NUMBER_DNE_ALREADY_EXCLUDED_BY_RANGE_REDUNDANCY", 21, 21, false);
//            NUMBER_CANNOT_EQUAL_AND_NOT_EQUAL_CONFLICTING = new eConflictType("NUMBER_CANNOT_EQUAL_AND_NOT_EQUAL_CONFLICTING", 22, 22, true);
//            NUMBER_RANGES_WILL_NEVER_MEET_CONFLICTING = new eConflictType("NUMBER_RANGES_WILL_NEVER_MEET_CONFLICTING", 23, 23, true);
//            eConflictType aeconflicttype[] = new eConflictType[24];
//            aeconflicttype[0] = SINGLE_CHOICE_LIST_REDUNDANCY;
//            aeconflicttype[1] = SINGLE_CHOICE_LIST_CONFLICTING;
//            aeconflicttype[2] = STRING_BEGINS_WITH_REDUNDANCY;
//            aeconflicttype[3] = STRING_BEGINS_WITH_CONFLICTING;
//            aeconflicttype[4] = STRING_ENDS_WITH_REDUNDANCY;
//            aeconflicttype[5] = STRING_ENDS_WITH_CONFLICTING;
//            aeconflicttype[6] = STRING_EQUALS_REDUNDANCY;
//            aeconflicttype[7] = STRING_EQUALS_CONFLICTING;
//            aeconflicttype[8] = STRING_CONTAINS_REDUNDANCY;
//            aeconflicttype[9] = STRING_CONTAINS_AND_DOES_NOT_CONTAIN_CONFLICTING;
//            aeconflicttype[10] = DATE_ON_DATE_REDUNDANCY;
//            aeconflicttype[11] = DATE_ON_DATE_CONFLICTING;
//            aeconflicttype[12] = DATE_ON_OR_AFTER_REDUNDANCY;
//            aeconflicttype[13] = DATE_ON_OR_BEFORE_REDUNDANCY;
//            aeconflicttype[14] = DATE_RANGES_WILL_NEVER_CROSS_CONFLICTING;
//            aeconflicttype[15] = DATE_RESULTS_WILL_BE_ONLY_ONE_DAY_REDUNDANCY;
//            aeconflicttype[16] = NUMBER_IDENTICAL_RULES_REDUNDANCY;
//            aeconflicttype[17] = NUMBER_MULTIPLE_EQUALITY_CONFLICT;
//            aeconflicttype[18] = NUMBER_ONE_RANGE_CONTAINS_ANOTHER_REDUNDANCY;
//            aeconflicttype[19] = NUMBER_EQUALITY_OUT_OF_RANGE_CONFLICTING;
//            aeconflicttype[20] = NUMBER_WILL_ONLY_SHOW_EQUALITY_REDUNDANCY;
//            aeconflicttype[21] = NUMBER_DNE_ALREADY_EXCLUDED_BY_RANGE_REDUNDANCY;
//            aeconflicttype[22] = NUMBER_CANNOT_EQUAL_AND_NOT_EQUAL_CONFLICTING;
//            aeconflicttype[23] = NUMBER_RANGES_WILL_NEVER_MEET_CONFLICTING;
//            $VALUES = aeconflicttype;
//        }
//
//        private eConflictType(String s, int i, int j, boolean flag)
//        {
//            super(s, i);
//            conflictIndex = j;
//            isConflict = flag;
//        }
//    }

}
