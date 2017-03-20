package com.sulan.webshell.entities;

import java.util.List;

/**
 * Created by huangsx on 2016/11/10.
 */

public class Data_Figure_RT_GetByAlarm_Resp extends BaseResponse {

    /**
     * $id : 1
     * MItemID : MI001051
     * DT_Refresh : 2016-11-02T15:08:15.353
     * ValidFlag : true
     * Alarm : RW3
     * Grade :
     * Tag :
     * Value : 218.21
     * EntityKey : {"$id":"2","EntitySetName":"Data_Figure_RT","EntityContainerName":"DataCenterEntities","EntityKeyValues":[{"Key":"MItemID","Type":"System.String","Value":"MI001051"}]}
     */

    private List<ListEntity> list;

    public List<ListEntity> getList() {
        return list;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity {
        private String $id;
        private String MItemID;
        private String DT_Refresh;
        private boolean ValidFlag;
        private String Alarm;
        private String Grade;
        private String Tag;
        private double Value;
        /**
         * $id : 2
         * EntitySetName : Data_Figure_RT
         * EntityContainerName : DataCenterEntities
         * EntityKeyValues : [{"Key":"MItemID","Type":"System.String","Value":"MI001051"}]
         */

        private EntityKeyEntity EntityKey;

        public String get$id() {
            return $id;
        }

        public void set$id(String $id) {
            this.$id = $id;
        }

        public String getMItemID() {
            return MItemID;
        }

        public void setMItemID(String MItemID) {
            this.MItemID = MItemID;
        }

        public String getDT_Refresh() {
            return DT_Refresh;
        }

        public void setDT_Refresh(String DT_Refresh) {
            this.DT_Refresh = DT_Refresh;
        }

        public boolean isValidFlag() {
            return ValidFlag;
        }

        public void setValidFlag(boolean ValidFlag) {
            this.ValidFlag = ValidFlag;
        }

        public String getAlarm() {
            return Alarm;
        }

        public void setAlarm(String Alarm) {
            this.Alarm = Alarm;
        }

        public String getGrade() {
            return Grade;
        }

        public void setGrade(String Grade) {
            this.Grade = Grade;
        }

        public String getTag() {
            return Tag;
        }

        public void setTag(String Tag) {
            this.Tag = Tag;
        }

        public double getValue() {
            return Value;
        }

        public void setValue(double Value) {
            this.Value = Value;
        }

        public EntityKeyEntity getEntityKey() {
            return EntityKey;
        }

        public void setEntityKey(EntityKeyEntity EntityKey) {
            this.EntityKey = EntityKey;
        }

        public static class EntityKeyEntity {
            private String $id;
            private String EntitySetName;
            private String EntityContainerName;
            /**
             * Key : MItemID
             * Type : System.String
             * Value : MI001051
             */

            private List<EntityKeyValuesEntity> EntityKeyValues;

            public String get$id() {
                return $id;
            }

            public void set$id(String $id) {
                this.$id = $id;
            }

            public String getEntitySetName() {
                return EntitySetName;
            }

            public void setEntitySetName(String EntitySetName) {
                this.EntitySetName = EntitySetName;
            }

            public String getEntityContainerName() {
                return EntityContainerName;
            }

            public void setEntityContainerName(String EntityContainerName) {
                this.EntityContainerName = EntityContainerName;
            }

            public List<EntityKeyValuesEntity> getEntityKeyValues() {
                return EntityKeyValues;
            }

            public void setEntityKeyValues(List<EntityKeyValuesEntity> EntityKeyValues) {
                this.EntityKeyValues = EntityKeyValues;
            }

            public static class EntityKeyValuesEntity {
                private String Key;
                private String Type;
                private String Value;

                public String getKey() {
                    return Key;
                }

                public void setKey(String Key) {
                    this.Key = Key;
                }

                public String getType() {
                    return Type;
                }

                public void setType(String Type) {
                    this.Type = Type;
                }

                public String getValue() {
                    return Value;
                }

                public void setValue(String Value) {
                    this.Value = Value;
                }
            }
        }
    }
}
